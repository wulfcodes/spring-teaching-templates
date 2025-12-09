package com.dj.core.init.dependency;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy      // loads upon the first time it's used
public class Sleeper {}
