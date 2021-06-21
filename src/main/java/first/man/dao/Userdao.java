package first.man.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import first.man.model.User;

@Repository
public interface Userdao extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email = ?1")
    public User findUserByEmail(String mail);
}
