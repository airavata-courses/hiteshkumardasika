package entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Order(Ordered.HIGHEST_PRECEDENCE)
@CrossOrigin(origins = "*")
@RequestMapping("/fb")
public class MainController {

    @Autowired
    RegisterService registerService;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "working";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String registerUser(@RequestBody User user) {
        registerService.registerUser(user);
        return "{ \"result\":\"success\"}";
    }

    @RequestMapping(value = "/{username}/{password}", method = RequestMethod.GET)
    public String makeLoginUrl(@PathVariable("username") String username, @PathVariable("password") String password) {
        return registerService.checkIfUserLoggedIn(username, password);
    }
}
