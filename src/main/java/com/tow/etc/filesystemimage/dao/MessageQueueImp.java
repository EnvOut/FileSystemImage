package com.tow.etc.filesystemimage.dao;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueueImp implements MessageQueue {
    private Queue<String> queue = new ConcurrentLinkedQueue<>();

    @Override
    public String toString() {
        return queue.toString();
    }

    @Override
    public boolean hasNext() {
        return size() != 0 ? true : false;
    }

    @Override
    public void add(String stringPath) {
        queue.add(stringPath);
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public String pop() {
        return queue.poll();
    }

    @Override
    public String get() {
        return queue.peek();
    }
}
