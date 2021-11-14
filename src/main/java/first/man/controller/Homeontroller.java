package first.man.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import first.man.helper.Message;
import first.man.model.User;
import first.man.service.Userservice;

@RestController
public class Homeontroller {
    @Autowired
    private Userservice service;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping("/")
    public ModelAndView home() {
        List<User> users = service.getallusers();
        return new ModelAndView("home", "users", users);
    }
//this is nanda kishore code

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("user") User user) {
        return new ModelAndView("signup");
    }

    @PostMapping("/signup")
    public ModelAndView signup(Model m, @Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpSession session, @RequestParam(name = "agreement", defaultValue = "false") boolean agreement) {
        try {
            if (bindingResult.hasErrors()) {
                System.out.println("error message" + bindingResult.toString());
                m.addAttribute("user", user);

                return new ModelAndView("signup");
            }

            if (!agreement) {
                throw new Exception("you have not agreed the agreement");
            }

            user.setEnabled(true);
            user.setRole("USER");
            user.setImageurl("imageurl");
            user.setPassword(encoder.encode(user.getPassword()));
            service.saveuser(user);
            session.setAttribute("message", new Message("sucessfully Registerd", "alert alert-sucess"));
            return new ModelAndView("signup", "user", new User());

        } catch (Exception e) {
            session.setAttribute("message", new Message("somethings went wrong" + e.getMessage(), "alert alert-danger"));
            return new ModelAndView("signup");

        }

    }

    @RequestMapping("/signin")
    public ModelAndView login() {
        return new ModelAndView("loginpage");
    }

    @RequestMapping("/testsignup")
    public ModelAndView testsignup(Model m, @Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpSession session, @RequestParam(name = "agreement", defaultValue = "false") boolean agreement) {
        try {
            if (bindingResult.hasErrors()) {
                System.out.println("error message" + bindingResult.toString());
                m.addAttribute("user", user);

                return new ModelAndView("register");
            }

            if (!agreement) {
                throw new Exception("you have not agreed the agreement");
            }

            user.setEnabled(true);
            user.setRole("user");
            user.setImageurl("imageurl");
//            user.setPassword(encoder.encode(user.getPassword()));
            service.saveuser(user);
            session.setAttribute("message", new Message("sucessfully Registerd", "alert-sucess"));
            return new ModelAndView("register", "user", new User());

        } catch (Exception e) {
            session.setAttribute("message", new Message("somethings went wrong" + e.getMessage(), "alert-danger"));
            return new ModelAndView("register");

        }

    }

    @RequestMapping("/index")
    public String test1() {
        return "" + service.getuserbyemail("pavan");
    }
    @RequestMapping("/test")
    public  ModelAndView testquery() {
    	return new ModelAndView("testjquery");
    }

}
