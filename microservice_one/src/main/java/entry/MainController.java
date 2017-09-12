package entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeoutException;

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

    @RequestMapping(value = "/item", method = RequestMethod.POST, consumes = "text/plain")
    public String createTodoItem(@RequestBody String payload) throws Exception {
/*
        URL url = new URL("http://localhost:5000/item");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
*/

        return new RPCClient().call(payload);
    }

    @RequestMapping(value = "/item/{userId}", method = RequestMethod.GET, consumes = "text/plain")
    public String fetchTodoItem(@PathVariable("userId") String userId) throws Exception {
        return new RPCClient().call(userId);
    }

}
