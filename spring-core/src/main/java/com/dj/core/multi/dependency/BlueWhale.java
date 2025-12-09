package com.dj.core.multi.dependency;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("blue")
public class BlueWhale implements Whale {
    @Override
    public String getName() {
        return "Blue Whale";
    }
}
