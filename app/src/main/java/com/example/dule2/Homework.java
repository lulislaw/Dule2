package com.example.dule2;

public class Homework {
    String id, subject, work;

    public Homework() {
    }

    @Override
    public String toString() {
        return id + " " + subject + " " + work;
    }

    public Homework(String id, String subject, String work) {
        this.id = id;
        this.subject = subject;
        this.work = work;
    }


}