package com.tow.etc.filesystemimage.controller;

import com.tow.etc.filesystemimage.dao.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
public class FilesystemScanner implements Runnable {
    private static Logger log = Logger.getLogger(FilesystemScanner.class.getSimpleName());

    @Autowired
    private MessageQueue messageQueue;

    @Value("${dir.start}")
    private String dirStart;

    @Override
    public void run() {
        getFilesRecursive(Paths.get(dirStart));
    }

    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

    public void getFilesRecursive(Path directory) {
//        log.info(directory.toAbsolutePath().toString());
        if (Files.exists(directory) && Files.isDirectory(directory)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
                for (Path item : dirStream) {
                    if (Files.isDirectory(item)) {
                        getFilesRecursive(item);
                    } else if (Files.isRegularFile(item)) {
                        messageQueue.add(item.toAbsolutePath().toString());
                    } else {
                        log.info("Dont file and directory: " + item.toAbsolutePath());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }
}
