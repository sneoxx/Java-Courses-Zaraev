package com.zaraev.epam;

public class Main {
    public static void main(String[] args) {
        MainHelper mainHelper = new MainHelper();
        mainHelper.callRaceCondition();
        mainHelper.callsSynchronizedRaceCondition();
        mainHelper.breakingTheDeadlock();
        mainHelper.deadlock(); //не в 100% случаев
    }
}