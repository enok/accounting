package br.com.accounting.business.aspect;

import br.com.accounting.business.service.GenericTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HistoryAspectTest extends GenericTest {
    @Autowired
    private HistoryAspect historyAspect;

    @Test(expected = NullPointerException.class)
    public void saveHistoryException() {
        historyAspect.saveHistory(null);
    }

    @Test
    public void saveHistoryMethod() {
        historyAspect.saveHistoryMethod();
    }
}
