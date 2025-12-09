package com.dj.core.ioc;

import com.dj.core.ioc.dependency.Alpha;

class TightCoupling {

    // Class Level Object
    private Alpha obj1 = new Alpha();


    public void fun() {
        // Method Level Object
        Alpha obj2 = new Alpha();

        obj1.use();
        obj2.use();
    }
}

class LooseCoupling {

    // Class Level Object
    private Alpha obj1;

    // Injection Point
    public LooseCoupling(Alpha obj1) {
        this.obj1 = obj1;
    }

    //Injection Point
    public void fun(Alpha obj2) {
        // Method Level Object

        obj1.use();
        obj2.use();
    }
}

public class Coupling {


    public static void main(String[] args) {
        // Responsibility of Maintainer
        TightCoupling tight = new TightCoupling();
        tight.fun();


        // Responsibility of User
        Alpha obj1 = new Alpha();
        Alpha obj2 = new Alpha();

        LooseCoupling loose = new LooseCoupling(obj1);
        loose.fun(obj2);
    }
}
