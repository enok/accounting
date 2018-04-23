package br.com.accounting.core.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static br.com.accounting.core.util.Utils.getDoubleFromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
public class UtilsTest {

    @Test
    public void getDoubleFromStringNullValue() {
        Double doubleFromString = getDoubleFromString(null);
        assertThat(doubleFromString, nullValue());
    }

    @Test(expected = RuntimeException.class)
    public void getDoubleFromStringParseException() {
        Double doubleFromString = getDoubleFromString("abc");
        System.out.println(doubleFromString);
    }
}
