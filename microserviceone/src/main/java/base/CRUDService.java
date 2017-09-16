package base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public class CRUDService {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public String deleteFromList(String itemId) {
        try {
            String insertQurey = "DELETE FROM todo_db.todoList where itemId= " + "'" + itemId + "'";
            jdbcTemplate.execute(insertQurey);
            return "success";
        } catch (DataAccessException dae) {
            return "failure";
        }

    }

    public List<TodoListItem> searchForTodoList(String searchTerm) {
        try {
            String searchQuery = "SELECT * FROM todoList WHERE item LIKE '%" + searchTerm + "%'";
            List<TodoListItem> todoListItems = jdbcTemplate.queryForList(searchQuery, TodoListItem.class);
            return todoListItems;

        } catch (DataAccessException dae) {
            return null;
        }

    }


}
