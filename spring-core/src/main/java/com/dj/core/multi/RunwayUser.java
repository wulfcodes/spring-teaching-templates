package com.dj.core.multi;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.dj.core.multi.dependency.Plane;

@Component
public class RunwayUser {

    @Autowired
    @Qualifier("fighterPlane")  // using default spring bean name
    private Plane plane1;

    @Autowired
    @Qualifier("cargoPlane")    // using default spring bean name
    private Plane plane2;

    @Autowired
    private List<Plane> planes;

    public void flyPriority() {
        System.out.println("Flying most important plane");
        plane1.fly();
    }

    public void flyInsignificant() {
        System.out.println("Flying least significant plane");
        plane2.fly();
    }

    public void flyInOrder() {
        System.out.println("Flying in order");
        for (Plane plane : planes)
            plane.fly();
    }

}
