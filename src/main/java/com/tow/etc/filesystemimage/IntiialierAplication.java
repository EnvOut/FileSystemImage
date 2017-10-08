package com.tow.etc.filesystemimage;

import com.tow.etc.filesystemimage.configuration.Configuration;
import com.tow.etc.filesystemimage.controller.FileCreator;
import com.tow.etc.filesystemimage.controller.FilesystemScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;

public class IntiialierAplication {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration.class);

        long start = new Date().getTime();
        FilesystemScanner filesystemScanner = ctx.getBean("filesystemScanner", FilesystemScanner.class);
        new Thread(filesystemScanner).start();
        ArrayList<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FileCreator fileCreator = ctx.getBean("fileCreator", FileCreator.class);
            Thread threadInstance = new Thread(fileCreator);
            threadInstance.start();
            threadList.add(threadInstance);
        }

        for (Thread item:threadList){
            try {
                item.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = new Date().getTime();
        System.out.println(end-start);
    }
}
