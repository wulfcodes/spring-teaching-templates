package com.dj.core.multi.dependency;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class CargoPlane implements Plane {
    @Override
    public void fly() {
        System.out.println("Flying Cargo Plane");
    }
}
