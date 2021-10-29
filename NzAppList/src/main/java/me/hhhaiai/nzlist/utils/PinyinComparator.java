package me.hhhaiai.nzlist.utils;

import me.hhhaiai.nzlist.model.AppModel;

import java.util.Comparator;

public class PinyinComparator implements Comparator<AppModel> {

    @Override
    public int compare(AppModel o1, AppModel o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
