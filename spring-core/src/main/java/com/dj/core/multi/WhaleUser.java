package com.dj.core.multi;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dj.core.multi.dependency.Whale;

@Component
public class WhaleUser {

    @Autowired
    private List<Whale> whales;

    public void fact() {
        System.out.println("Some whales that live in the ocean are: ");
        for (Whale whale : whales) {
            System.out.println(whale.getName());
        }
    }

}
