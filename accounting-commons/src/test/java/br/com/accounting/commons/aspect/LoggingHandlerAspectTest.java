package br.com.accounting.commons.aspect;

import br.com.accounting.commons.test.GenericTest;
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
