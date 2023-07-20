package jpa.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j      //log문 쓸 수 있게끔 해줌
public class HomeController {

    
    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
