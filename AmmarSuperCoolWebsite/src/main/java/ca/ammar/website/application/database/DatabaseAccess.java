/*
 * Syed Ammar Waseem
 * Online website
 * SDNE(Software Development Network Engineering)
 *
 */
package ca.ammar.website.application.database;

import ca.ammar.website.application.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class DatabaseAccess {

    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private void namedParameterJdbcTemplate (NamedParameterJdbcTemplate jdbc){
       this.jdbc = jdbc;
    }

    public User findUserAccount(String email){
        String sql = "SELECT * FROM users WHERE username=:email;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("email", email);
        ArrayList<User> users = (ArrayList<User>) jdbc.query
                (sql, params, new BeanPropertyRowMapper<User>(User.class));
        if (users.size() > 0){
            return users.get(0);
        }else
            return null;
    }

    public List<String> getRolesByID(long userId){
        String sql = "SELECT user_role.userid, roles.rolename FROM user_role, roles WHERE user_role.roleid = roles.roleid AND user_role.userid=:userid;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userid", userId);
        ArrayList<String> roles = new ArrayList<String>();
        List<Map<String, Object>> rows = jdbc.queryForList(sql, params);
        for(Map<String, Object> row : rows){
            roles.add((String)row.get("roleName"));
        }
        return roles;
    }

    public long getUserIdByName(String name){
        String sql = "Select userId from users where username=:name;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        long id = 0;
        List<Map<String, Object>> rows = jdbc.queryForList(sql, params);
        for(Map<String, Object> row : rows){
            id = (int) row.get("userId");
        }
        return id;
    }
}
