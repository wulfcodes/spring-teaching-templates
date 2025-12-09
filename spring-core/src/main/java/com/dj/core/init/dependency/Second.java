package com.dj.core.init.dependency;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("first")     // waits for bean with name first to load
public class Second {}
