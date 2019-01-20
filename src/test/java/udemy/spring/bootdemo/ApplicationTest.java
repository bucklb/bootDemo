package udemy.spring.bootdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import udemy.spring.bootdemo.Controller.*;
import udemy.spring.bootdemo.Service.*;


@RunWith(SpringRunner.class)
//@SpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @Autowired
    private HomeController homeController;
    @Autowired
    private QuoteController quoteController;
    @Autowired
    private HeadedController headedController;
    @Autowired
    private QuoteService quoteService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Make sure it seems to be starting and getting sensible values
    @Test
    public void contextLoads() throws Exception {

        // Just check that things get auto injected
        assertThat(homeController,   is(notNullValue()));
        assertThat(quoteController,  is(notNullValue()));
        assertThat(headedController, is(notNullValue()));

        assertThat(quoteService,     is(notNullValue()));
    }

    // Test the home page (doesn't rely on external site or swagger) replies with expected text (that contains there's "no place")
    @Test
    public void homeShouldReturnExpectedMessage() throws Exception {

        System.out.println("test is using " + port);
        String txt=this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
        assertThat(txt.contains("no place"), is(true));

    }







}
