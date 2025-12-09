package com.dj.core.ioc.dependency;

public class Beta implements Sigma {
    private static int counter = 0;
    private int num;

    public Beta() {
        num = ++counter;
    }

    @Override
    public void use() {
        System.out.println("Using Beta obj " + num);
    }
}
