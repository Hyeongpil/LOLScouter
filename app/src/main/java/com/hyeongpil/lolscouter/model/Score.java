package com.hyeongpil.lolscouter.model;

import java.util.Stack;

/**
 * Created by hyeongpil on 2017-01-02.
 */
public class Score {

    int totalScore; // 현재 총점
    Stack<Integer> beforeScore = new Stack<>(); // 이 전 문제에서 선택한 점수

    private static Score ourInstance = new Score();

    public static Score getInstance() {
        return ourInstance;
    }

    private Score() {
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Stack<Integer> getBeforeScore() {
        return beforeScore;
    }
}
