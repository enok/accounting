package br.com.accounting.commons.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;

import static br.com.accounting.commons.util.Utils.getDoubleFromString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
public class UtilsTest {

    @Test
    public void getDoubleFromStringNullValue() throws ParseException {
        Double doubleFromString = getDoubleFromString(null);
        assertThat(doubleFromString, nullValue());
    }

    @Test(expected = ParseException.class)
    public void getDoubleFromStringParseException() throws ParseException {
        getDoubleFromString("abc");
    }
}
