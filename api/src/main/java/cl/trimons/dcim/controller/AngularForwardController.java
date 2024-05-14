package cl.trimons.dcim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AngularForwardController {
    @GetMapping("{path:^(?!api|assets|public|swagger)[^\\.]*}/**")
    public String handleForward() {
        return "forward:/";
    }
}
