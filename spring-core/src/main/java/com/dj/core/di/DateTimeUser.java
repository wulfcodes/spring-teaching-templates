package com.dj.core.di;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateTimeUser {

    @Autowired
    private LocalDateTime localDateTime;

    public void showDateTime() {
        System.out.println(localDateTime.toString());
    }

}
