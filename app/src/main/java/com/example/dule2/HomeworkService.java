package com.example.dule2;

import android.util.Log;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class HomeworkService {
    private ArrayList<Homework> homeworks = new ArrayList<Homework>();

    Faker faker = new Faker();
    String id, subject, work;

    public void init() {
        faker = Faker.instance();



        ArrayList<Homework> generatedHomework = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            id = String.valueOf(i);
            subject = faker.job().title();
            work = faker.job().title();
            generatedHomework.add(new Homework(id, subject, work));
        }

        System.out.println(generatedHomework);

    }

    private List<Homework> getHomeworks() {
        return homeworks;
    }

    private void deleteHomework(Homework homework) {
        int indexToDelete = homeworks.indexOf(id == homework.id);
        if (indexToDelete != -1) {
            homeworks.remove(indexToDelete);
        }
    }

    private void moveHomework(Homework homework, int moveBy) {
        int oldIndex = homeworks.indexOf(id == homework.id);
        if (oldIndex == -1) return;
        int newIndex = oldIndex + moveBy;
        if (newIndex < 0 || newIndex >= homeworks.size()) return;
        Collections.swap(homeworks, oldIndex, newIndex);
    }

}
