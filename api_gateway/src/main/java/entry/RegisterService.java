package entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class RegisterService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void registerUser(User user) {
        String insertQurey = "INSERT INTO user (userId, userName, email, password, screenName) VALUES(?, ?, ?, ?, ?)";

        Object[] params = new Object[]{user.getUserId(), user.getUserName(), user.getEmail(), user.getPassword(), user.getScreenName()};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,};
        jdbcTemplate.update(insertQurey, params, types);
    }

    public String checkIfUserLoggedIn(String userName, String password) {
        String getQuery = "SELECT * FROM todo_db.user WHERE userName= " + "'"+userName+"'" + " and password= " + "'"+password+"'" + ";";
        List query = jdbcTemplate.query(getQuery, new BeanPropertyRowMapper(User.class));
        if (query.size()>0){
            return "Success Logged IN!!";
        }else {
            return "Failed To Login!!";
        }
    }
}
