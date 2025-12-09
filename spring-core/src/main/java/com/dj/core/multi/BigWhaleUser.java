package com.dj.core.multi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.dj.core.multi.dependency.Whale;

@Component
public class BigWhaleUser {

    @Autowired
    @Qualifier("blue")
    private Whale whale;

    public void fact() {
        System.out.println("The biggest whale in the ocean is " + whale.getName());
    }

}
