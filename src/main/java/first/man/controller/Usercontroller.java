package first.man.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;

import first.man.dao.Contactdao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import first.man.dao.Userdao;
import first.man.helper.Message;
import first.man.model.Contact;
import first.man.model.User;

@RestController
@RequestMapping("/user")
public class Usercontroller {
    @Autowired
    private Userdao repos;
    @Autowired
    private Contactdao contactrepos;


    @RequestMapping("/")
    public ModelAndView first(Principal principal) {
        String name = principal.getName();
        User user = repos.findUserByEmail(name);
        return new ModelAndView("user/userdashboard", "user", user);
    }

    @RequestMapping("/addcontact")
    public ModelAndView addcontact() {
        return new ModelAndView("user/addcontact", "contact", new Contact());
    }

    @PostMapping("/savecontact")
    public ModelAndView savecontact(@ModelAttribute("contact") Contact contact,
                                    @RequestParam("contactimage") MultipartFile multipartFile, Principal principal, HttpSession session,
                                    Model m) {
        try {
            contact.setImage(multipartFile.getOriginalFilename());

            File file = new ClassPathResource("static/image/").getFile();
            Files.copy(multipartFile.getInputStream(),
                    Paths.get(file.getAbsolutePath() + File.separator + multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            List<Contact> contact1 = List.of(contact);
            String name = principal.getName();
            User user = repos.findUserByEmail(name);
            user.getContacts().add(contact);
            contact.setUser(user);
            repos.save(user);
            session.setAttribute("message", new Message("Contact added to the user", "alert-success"));
            System.err.println(user.getEmail());
        } catch (Exception e) {
            session.setAttribute("message", new Message("unable to add due to " + e.getMessage(), "alert-danger"));
            System.out.println(e.getMessage());
        }
        return new ModelAndView("redirect:/user/addcontact");
    }

    @RequestMapping("/viewcontacts")
    public ModelAndView viewcontact(Principal principal, Model m) {
        String name = principal.getName();
        User user = repos.findUserByEmail(name);
        List<Contact> contacts = contactrepos.findContactsByUser_Userid(user.getUserid());



        return new ModelAndView("user/viewcontacts","contacts",contacts);
    }

}
