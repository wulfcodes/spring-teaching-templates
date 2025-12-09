package com.dj.core.di;

/* Circular Dependency */

class Dependency1 {
    // Autowired
    private Dependency2 dependency2;
}

class Dependency2 {
    // Autowired
    private Dependency1 dependency1;
}

/* No Circular Dependency, as each object will be created first, then injected */

class Dependency3 {
    private Dependency4 dependency4;

    // Autowired
    public void setDependency(Dependency4 dependency4) {
        this.dependency4 = dependency4;
    }
}

class Dependency4 {
    private Dependency3 dependency3;

    // Autowired
    public void setDependency(Dependency3 dependency3) {
        this.dependency3 = dependency3;
    }


}

public class CircularDependency {
}
