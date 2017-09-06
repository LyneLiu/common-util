package com.lyne.guava.collect;

/**
 * Created by nn_liu on 2016/11/10.
 */


import com.beust.jcommander.internal.Lists;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Guava的Multimap就提供了一个方便地把一个键对应到多个值的数据结构.
 */

class StudentScore {
    private int CourseId;
    private int score;

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

public class MultiMapDemo {

    @Test
    public void teststuScoreMultimap() {
        Multimap<String, StudentScore> scoreMultimap = ArrayListMultimap.create();
        for (int i = 10; i < 20; i++) {
            StudentScore studentScore = new StudentScore();
            studentScore.setCourseId(1001 + i);
            studentScore.setScore(100 - i);
            scoreMultimap.put("peida", studentScore);
        }
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys().toString());

        for (StudentScore studentScore : Lists.newArrayList(scoreMultimap.get("peida"))) {
            System.out.println("studentScore:" + studentScore);
        }

    }

}
