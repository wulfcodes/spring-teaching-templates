package com.dj.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.dj.core.di.DateTimeUser;
import com.dj.core.di.DependencyUser;
import com.dj.core.di.StudentUser;
import com.dj.core.multi.BigWhaleUser;
import com.dj.core.multi.DeadlyWhaleUser;
import com.dj.core.multi.PlanUser;
import com.dj.core.multi.RunwayUser;
import com.dj.core.props.PropUser;
import com.dj.core.scope.BeanUser;

@Component
public class CoreApplicationRunner implements CommandLineRunner {

    @Autowired
    private DependencyUser dependencyUser;

    @Autowired
    private DateTimeUser dateTimeUser;

    @Autowired
    private StudentUser studentUser;

    @Autowired
    private DeadlyWhaleUser deadlyWhaleUser;

    @Autowired
    private BigWhaleUser bigWhaleUser;

    @Autowired
    private PlanUser planUser;

    @Autowired
    private RunwayUser runwayUser;

    @Autowired
    private BeanUser beanUser;

    @Autowired
    private PropUser propUser;

    @Override
    public void run(String... args) throws Exception {
        dependencyUser.fun();

        dateTimeUser.showDateTime();

        studentUser.useStudent();

        deadlyWhaleUser.fact();

        bigWhaleUser.fact();

        planUser.fun();

        runwayUser.flyPriority();
        runwayUser.flyInsignificant();
        runwayUser.flyInOrder();

        beanUser.checkBeans();

        propUser.checkProps();
        propUser.checkBasicProps();
        propUser.checkComplexProps();
    }

}
