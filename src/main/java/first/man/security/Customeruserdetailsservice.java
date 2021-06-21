package first.man.security;

import first.man.dao.Userdao;
import first.man.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//this is user details service
public class Customeruserdetailsservice implements UserDetailsService {
    @Autowired
    private Userdao repos;

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User username = repos.findUserByEmail(name);
        System.err.println("username is" + username);
        if (username == null) {
            throw new UsernameNotFoundException("username not found");
        }
        Customuserdetails customuserdetails = new Customuserdetails(username);
        return customuserdetails;
    }
}
