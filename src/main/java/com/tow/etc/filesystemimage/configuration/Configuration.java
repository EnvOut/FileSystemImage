package com.tow.etc.filesystemimage.configuration;

import com.tow.etc.filesystemimage.controller.FileCreator;
import com.tow.etc.filesystemimage.controller.FilesystemScanner;
import com.tow.etc.filesystemimage.dao.MessageQueue;
import com.tow.etc.filesystemimage.dao.MessageQueueImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configurable
@ComponentScan(basePackages = {"com.tow.etc.filesystemimage"})
@PropertySource("classpath:ini.properties")
public class Configuration {

    @Value("${dir.results}")
    private String resultsDirectory;

    @Value("${dir.start}")
    private String imageDirectory;

    @Bean
    @Scope("prototype")
    public FileCreator getFileCreator(@Autowired MessageQueue messageQueue) {
        return new FileCreator();
    }

    @Bean
    public MessageQueue getQueue() {
        System.out.println("que created");
        return new MessageQueueImp();
    }

    @Bean
    public FilesystemScanner getFilesystemScanner(@Autowired MessageQueue messageQueue) {
        return new FilesystemScanner();
    }


}
