package com.dj.core.multi.dependency;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("orca")
public class OrcaWhale implements Whale {
    @Override
    public String getName() {
        return "Orca Whale";
    }
}
