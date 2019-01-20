package udemy.spring.bootdemo.Domain;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import udemy.spring.bootdemo.Domain.Value;



/**
 *   Very basic test
 */
public class ValueTest {

    long ID      = 69l;
    String QUOTE = "pithy words";

    @Test
    public void testConstructor() {
        udemy.spring.bootdemo.Domain.Value value = new udemy.spring.bootdemo.Domain.Value( ID, QUOTE );
        assertThat( value.getId(),    is(ID) );
        assertThat( value.getQuote(), is(QUOTE) );
    }



}
