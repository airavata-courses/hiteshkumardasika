package base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@Order(Ordered.HIGHEST_PRECEDENCE)
@CrossOrigin(origins = "*")
@RequestMapping("/fb")
public class MainController {

    @Autowired
    CRUDService crudService;

    @Autowired
    RPCServer rpcServer;

    @PostConstruct
    public void init(){
        rpcServer.startServer();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "working";
    }

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.POST)
    public String createTodoItem(@PathVariable("itemId") String itemId) throws Exception {
        return crudService.deleteFromList(itemId);
    }

    @RequestMapping(value = "/item/{searchTerm}", method = RequestMethod.GET)
    public List<TodoListItem> searchTodoList(@PathVariable("searchTerm") String searchTerm) throws Exception {
        return crudService.searchForTodoList(searchTerm);
    }


}
