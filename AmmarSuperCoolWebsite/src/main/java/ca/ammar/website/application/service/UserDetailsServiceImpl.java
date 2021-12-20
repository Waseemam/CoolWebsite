
package ca.ammar.website.application.service;

import ca.ammar.website.application.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Lazy @Autowired
    private DatabaseAccess db;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ca.ammar.website.application.beans.User user = db.findUserAccount(username);
        if (user == null){
            System.err.printf("User not found :%s%n", username);
            throw new UsernameNotFoundException("User " + username + "not found in database.");
        }

        ArrayList<String> rolesList = (ArrayList<String>) db.getRolesByID(db.getUserIdByName(username));

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

        if (rolesList != null){
            for (String role : rolesList){
                grantList.add(new SimpleGrantedAuthority(role));
            }
        }
        return new User(user.getUsername(), user.getPassword(), grantList);
    }
}
