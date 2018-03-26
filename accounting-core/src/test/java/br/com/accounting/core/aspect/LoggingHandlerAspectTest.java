package br.com.accounting.core.aspect;

import br.com.accounting.core.GenericTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LoggingHandlerAspectTest extends GenericTest {
    @Autowired
    private LoggingHandlerAspect loggingHandlerAspect;

    @Test
    public void publicMethod() {
        loggingHandlerAspect.publicMethod();
    }
}
