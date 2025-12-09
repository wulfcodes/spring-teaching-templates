package com.dj.core.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dj.core.multi.dependency.Plan;

@Component
public class PlanUser {

    @Autowired
    private Plan plan;

    public void fun() {
        System.out.println("Executing Plan: ");
        plan.getPlan();
    }
}
