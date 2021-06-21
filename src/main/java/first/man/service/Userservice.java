    package first.man.service;

import first.man.dao.Userdao;
import first.man.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import java.util.List;

@Service
@Transactional
public class Userservice {
    @Autowired
    private Userdao repos;

    public List<User> getallusers() {
        List<User> users = repos.findAll();
        return users;

    }

    public User saveuser(User user) {
        User user1 = repos.save(user);
        return user1;
    }

    public User getoneuser(int id) {
        User user = repos.findById(id).get();
        return user;
    }

    public void deleteuser(int id) {
        repos.deleteById(id);

    }public User getuserbyemail(String email){
        return repos.findUserByEmail(email);


    }
}
