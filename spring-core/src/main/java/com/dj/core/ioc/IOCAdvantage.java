package com.dj.core.ioc;

import com.dj.core.ioc.dependency.Alpha;
import com.dj.core.ioc.dependency.Beta;
import com.dj.core.ioc.dependency.Sigma;

class OwnResponsibility {
    // Responsibility of Object Creation and Object Usage is dependent on Maintainer

    private Alpha alpha = new Alpha();
    private Beta beta = new Beta();

    public void useAlpha() {
        alpha.use();
    }

    public void useBeta() {
        beta.use();
    }
}

class PartialResponsibility {
    // Responsibility of Object Creation is on User Object Usage is dependent on Maintainer

    private Alpha alpha;
    private Beta beta;

    public PartialResponsibility(Alpha alpha) {
        this.alpha = alpha;
    }

    public PartialResponsibility(Beta beta) {
        this.beta = beta;
    }

    public void useAlpha() {
        alpha.use();
    }

    public void useBeta() {
        beta.use();
    }

}

class NoResponsibility {
    // Responsibility of Object Creation and Object Usage is dependent on User

    private Sigma sigma;

    public NoResponsibility(Sigma sigma) {
        this.sigma = sigma;
    }

    public void useSigma() {
        sigma.use(); // Can be Alpha or Beta, on User preference
    }
}

public class IOCAdvantage {
    public static void main(String[] args) {

    }
}
