package com.dj.core.multi.dependency;

import org.springframework.stereotype.Component;

@Component
public class BackupPlan implements Plan {
    @Override
    public void getPlan() {
        System.out.println("Backup");
    }
}
