package entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@Order(Ordered.HIGHEST_PRECEDENCE)
@CrossOrigin(origins = "*")
@RequestMapping("/fb")
public class MainController {

    @Autowired
    RegisterService registerService;

    RestTemplate restTemplate = new RestTemplate();

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

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public String createTodoItem(@RequestBody UserItem payload) throws Exception {
        String answer = restTemplate.postForObject("http://ms2:5000/item", payload, String.class);
        return answer;
    }

    @RequestMapping(value = "/item/{userId}", method = RequestMethod.GET)
    public String fetchTodoItem(@PathVariable("userId") String userId) throws Exception {
        String forObject = restTemplate.getForObject("http://ms2:5000/item/" + userId, String.class);
        return forObject;
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.DELETE)
    public String deleteTodoItem(@PathVariable("itemId") String itemId) throws Exception {
        return restTemplate.postForObject("http://ms1:8082/microservice_one-1/fb/item/" + itemId, null, String.class);
    }

    @RequestMapping(value = "/item/{searchTerm}", method = RequestMethod.POST)
    public List<TodoListItem> searchForItem(@PathVariable("searchTerm") String searchTerm) throws Exception {
        ResponseEntity<List<TodoListItem>> exchange = restTemplate.exchange("http://ms1:8082/microservice_one-1/fb/item/" + searchTerm,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<TodoListItem>>() {
                });
        return exchange.getBody();
    }


}
