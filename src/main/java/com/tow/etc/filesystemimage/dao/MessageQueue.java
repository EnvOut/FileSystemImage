package com.tow.etc.filesystemimage.dao;

import java.nio.file.Path;

public interface MessageQueue {
    boolean hasNext();

    void add(String stringPath);

    int size();

    String pop();

    String get();
}
