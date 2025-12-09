package com.dj.core.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.dj.core.di.dependency.Alpha;
import com.dj.core.di.dependency.Beta;
import com.dj.core.di.dependency.Delta;
import com.dj.core.di.dependency.Gamma;
import com.dj.core.di.dependency.Zeta;

@Component
public class DependencyUser {

    @Autowired              // Field Injection
    private Alpha alpha;

    private Beta beta;

    private Gamma gamma;

    @Autowired
    private ApplicationContext applicationContext; /* IoC container, Another bean managed by spring */

    @Autowired              // Constructor Injection
    public DependencyUser(Beta beta) {
        this.beta = beta;
    }

    @Autowired
    public void setGamma(Gamma gamma) {  // Setter Injection
        this.gamma = gamma;
    }

    public void fun() {
        Zeta zeta = applicationContext.getBean(Zeta.class);

        alpha.fun();
        beta.fun();
        gamma.fun();
        zeta.fun();
    }

    public void fun(@Autowired Delta delta) {} // Method Param Injection

}
