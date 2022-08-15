package com.example.dule2;

import java.util.Objects;

public class SearchItem {
    private String name;

    public SearchItem(String name)
    {
        this.name = name;
    }

    public String getDayOfWeek()
    {
        return decY(name)[3] + "  " + decY(name)[1];
    }
    public String getCourse()
    {
        return decY(name)[4];
    }
    public String getName() {
        return decomposition(decY(name)[0])[0];}
    public String getTypeSubject() {
        return decomposition(decY(name)[0])[1];}
    public String getTeacher() {
    return decomposition(decY(name)[0])[2];
    }
    public String getRoom() {
        return decomposition(decY(name)[0])[3];
    }
    public String getTime() {
        return decY(name)[2];
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        SearchItem item = (SearchItem) o;
        return Objects.equals(name, item.name);
    }

    public int hashCode()
    {
        return Objects.hash(name);
    }


    private String[] decY(String SPLIT) {

        try
        {
            return SPLIT.split("Y");
        } catch (Exception e) {
            String[] null1 = new String[10];
            return null1;
        }

    }

    private String[] decomposition(String SPLIT) {

        String[] tempstring = new String[4];
        /*
         *  tempstring[0] - Название
         *  tempstring[1] - Тип
         *  tempstring[2] - Препод
         *  tempstring[3] - Аудитория
         */
        if (SPLIT.contains("(ЛЗ"))
            tempstring[1] = "Лабораторное занятие";
        else if (SPLIT.contains("(ПЗ"))
            tempstring[1] = "Практическое занятие";
        else if (SPLIT.contains("(Л "))
            tempstring[1] = "Лекция";
        else
            tempstring[1] = "";

        for (int le = 0; le < SPLIT.length(); le++) {
            if (SPLIT.charAt(le) == '(') {
                tempstring[0] = SPLIT.substring(0, le - 1);
                break;
            }
        }

        int tempsc = 0;

        for (int le = 0; le < SPLIT.length(); le++) {
            if (SPLIT.charAt(le) == ')') {
                tempsc = le + 2;
                break;
            }
        }
        tempstring[3] = "";
        tempstring[2] = "";
        try {

            if (SPLIT.contains("ЛК-")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 6, SPLIT.length());
                tempstring[2] = SPLIT.substring(tempsc, SPLIT.length() - 7);
            } else if (SPLIT.contains("У-") || SPLIT.contains("А-") || SPLIT.contains("ПА-")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 5, SPLIT.length());
                tempstring[2] = SPLIT.substring(tempsc, SPLIT.length() - 6);
            } else if (SPLIT.contains("этаж")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 9, SPLIT.length());

            } else if (SPLIT.contains("Спортивный комплекс")) {
                tempstring[3] = SPLIT.substring(SPLIT.length() - 19, SPLIT.length());
            } else
                tempstring[3] = "";


            if (tempstring[2].contains("\n")) {
                tempstring[2] = tempstring[2].substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return tempstring;
    }

}
