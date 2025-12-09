package com.dj.core.multi.dependency;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MajorPlan implements Plan {
    @Override
    public void getPlan() {
        System.out.println("Major");
    }
}
