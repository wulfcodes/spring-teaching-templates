package com.dj.core.multi.dependency;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class FighterPlane implements Plane {
    @Override
    public void fly() {
        System.out.println("Flying Fighter Plane");
    }
}
