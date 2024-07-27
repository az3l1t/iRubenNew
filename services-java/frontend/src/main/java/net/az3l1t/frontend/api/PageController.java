package net.az3l1t.frontend.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/success")
    public String toSuccess() {
        return "success.html";
    }
}
