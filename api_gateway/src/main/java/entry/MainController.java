package entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/fb")
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class MainController {

    @Autowired
    RegisterService registerService;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "working";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
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
        return new RPCClient().call(payload, "insert");
    }

    @RequestMapping(value = "/item/{userId}", method = RequestMethod.GET)
    public String fetchTodoItem(@PathVariable("userId") String userId) throws Exception {
        return new RPCClient().call(userId, "fetch");
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.DELETE)
    public String deleteTodoItem(@PathVariable("itemId") String itemId) throws Exception {
        return new RPCClient().callForOtherOperations(itemId, "delete");
    }

    @RequestMapping(value = "/item/{searchTerm}", method = RequestMethod.POST)
    public String searchForItem(@PathVariable("searchTerm") String searchTerm) throws Exception {
        return new RPCClient().callForOtherOperations(searchTerm, "search");
    }


}
