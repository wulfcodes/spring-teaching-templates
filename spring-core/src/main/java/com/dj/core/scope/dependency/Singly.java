package com.dj.core.scope.dependency;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope(BeanDefinition.SCOPE_SINGLETON) --> default is singleton
public class Singly {}
