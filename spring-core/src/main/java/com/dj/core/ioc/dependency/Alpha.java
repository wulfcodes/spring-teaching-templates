package com.dj.core.ioc.dependency;

public class Alpha implements Sigma {
    private static int counter = 0;
    private int num;

    public Alpha() {
        num = ++counter;
    }

    @Override
    public void use() {
        System.out.println("Using Alpha obj " + num);
    }
}
