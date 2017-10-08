package com.tow.etc.filesystemimage.controller;

import com.tow.etc.filesystemimage.dao.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Controller
@Configurable
public class FileCreator implements Runnable {
    private static Logger log = Logger.getLogger(FileCreator.class.getSimpleName());

    @Value("${maxFailAttempts}")
    private Integer maxFailAttempts;
    @Value("${dir.start}")
    private String dirStart;
    @Value("${dir.results}")
    private String dirResults;
    @Autowired
    private MessageQueue messageQueue;

    @Override
    public String toString() {
        return "FileCreator{" +
                "maxFailAttempts=" + maxFailAttempts +
                ", dirStart='" + dirStart + '\'' +
                ", dirResults='" + dirResults + '\'' +
                ", messageQueue=" + messageQueue +
                '}';
    }

    public void setMessageQueue(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void messageQueueIsNull() {
        toString();
    }

    @Override
    public void run() {
        try {
            Files.createDirectories(Paths.get(dirResults));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Integer countFailAttempts = 0; countFailAttempts < maxFailAttempts; ) {
            if (messageQueue.size() == 0) {
                countFailAttempts++;
                System.out.println("Sleep");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                String messagePath = messageQueue.pop();
                Path newPath = Paths.get(messagePath.replace(dirStart, dirResults));
                Path parent = newPath.getParent();
//                System.out.printf("dirStart: %s, dirResults: %s, newPath: %s\n",dirStart,dirResults,newPath);
//                log.info(newPath);
                try {
                    if (!Files.exists(parent)){
                        Files.createDirectories(parent);
                    }
                    Files.createFile(newPath);
//                    log.info("File created" + newPath);
                } catch (IOException e) {
                    log.warning("File don't created: " + newPath + "\n" + e.getMessage());
                }
            }
        }
    }
}
