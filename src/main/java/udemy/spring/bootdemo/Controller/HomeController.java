package udemy.spring.bootdemo.Controller;

// Needed for annotation
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 *  @Author     me
 *  @Purpose    handle stuff around home (could add actuator later?)
*/
@RequestMapping("")
@RestController
public class HomeController {

    String BR="<br>";

    // Need to push people to where swagger lives ...
    String REDIRECT_RTE="http://localhost:";
    String REDIRECT_PTH="/swagger-ui.html";
    int    REDIRECT_CDE=302;

    @Value("${server.port}")
    int APP_PORT;


    @RequestMapping(value="/info",method = RequestMethod.GET)
    public void getInfo(HttpServletResponse httpServletResponse) {
        String redirect=REDIRECT_RTE + APP_PORT + REDIRECT_PTH;
        httpServletResponse.setHeader("Location", redirect);
        httpServletResponse.setStatus(REDIRECT_CDE);
    }

    @RequestMapping(value="/swagger",method = RequestMethod.GET)
    public void getSwagger(HttpServletResponse httpServletResponse) {
        String redirect=REDIRECT_RTE + APP_PORT + REDIRECT_PTH;
        httpServletResponse.setHeader("Location", redirect);
        httpServletResponse.setStatus(REDIRECT_CDE);
    }

    // If someone hits the home stuff then redirect to swagger/info to get details

    // in case no link is requested, give them something to look at.  A clue as to what will respond would be good
    @RequestMapping(value="",method = RequestMethod.GET)
    public String getHome() {
        String s = "There's no place like it!!\n\n"+BR+BR+
                "swagger: get      : swagger documentation\n"+BR+
                "info:    get      : swagger documentation\n"+BR;
        return s;
    }



}
