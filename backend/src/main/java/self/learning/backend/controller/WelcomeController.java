package self.learning.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/")
    public String welcome(){
        return "Welcome to MyClass API Management. Go to /swagger-ui/index.html to see the API docs.";
    }
}
