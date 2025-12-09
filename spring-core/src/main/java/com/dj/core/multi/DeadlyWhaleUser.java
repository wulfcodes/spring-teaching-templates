package com.dj.core.multi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.dj.core.multi.dependency.Whale;

@Component
public class DeadlyWhaleUser {

    @Autowired
    @Qualifier("orca")
    private Whale whale;

    public void fact() {
        System.out.println("The deadliest whale in the ocean is " + whale.getName());
    }

}
