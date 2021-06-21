package first.man.dao;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import first.man.model.Contact;

import java.util.List;

@Repository
public interface Contactdao extends JpaRepository<Contact, Integer> {
//    @Query("select cid,firstname,lastname,email,phone from Contact  where user.userid =?1")
    public List<Contact> findContactsByUser_Userid(int userid);

}
