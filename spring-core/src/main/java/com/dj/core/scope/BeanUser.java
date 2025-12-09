package com.dj.core.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dj.core.scope.dependency.Multy;
import com.dj.core.scope.dependency.Singly;

@Component
public class BeanUser {

    @Autowired
    private Singly single1;
    private Singly single2;
    private Singly single3;

    @Autowired
    private Multy multiple1;
    private Multy multiple2;
    private Multy multiple3;


    @Autowired
    public BeanUser(Singly single2, Multy multiple2) {
        this.single2 = single2;
        this.multiple2 = multiple2;
    }

    @Autowired
    public void setSingle3(Singly single3) {
        this.single3 = single3;
    }

    @Autowired
    public void setMultiple3(Multy multiple3) {
        this.multiple3 = multiple3;
    }

    public void checkBeans() {
        System.out.printf("Singly:- %s %s %s\n", single1.hashCode(), single2.hashCode(), single3.hashCode());
        System.out.printf("Multy:- %s %s %s\n", multiple1.hashCode(), multiple2.hashCode(), multiple3.hashCode());
    }
}
