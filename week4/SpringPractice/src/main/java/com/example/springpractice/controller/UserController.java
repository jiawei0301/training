package com.example.springpractice.controller;

import com.example.springpractice.dto.UserDto;
import com.example.springpractice.entity.User;
import com.example.springpractice.exception.UserNotFoundException;
import com.example.springpractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller // @RestController = @Controller + @ResponseBody
// soap endpoints -----> xml request
// restful endpoints ------> stateless endpoints
// 1. HTTP method (verb) + URL ----> rule of thumb: don't contain any verbs


// localhost:8080/users/new
// 127.0.0.1:8080/users/new
// REAL-IP:CustomizedPort/users/new
// http://www.google.com === http://www.google:80.com
// https://www.google.com === https://www.google:443.com


// 1. HTTP method (verb)
// 2. URL ----> rule of thumb: don't contain any verbs
@RequestMapping("/users")
public class UserController {

    // injecting spring beans
    // DI ----> implmentation IoC ---> not a java Object, but a spring bean
    // Spring bean ???? ---> java object with lifecycyle management from spring container

    // Spring bean scope: singleton(default)
    // scopes: prototype, request, session, global Session

    // spring beans --> Spring container
    // 1. ApplicationContext -- eager loading, support AOP
    // 2. BeanFactory -- lazy loading, limited support AOP

    // DI ---> Three types:
    // construction injection // Springboot1.0: field injection officially recommended; SpringBoot2.0: construciton injections
    // field injection
    // setter injection

    // circular dependency ----> classA depends classB, class B depends on class C, class C depends on class A

    // class A {
    //   private B b;
    // }

    // class B {
    //   private C c;
    // }

    // class C {
        // @Lazy
    //   private A a;
    // }

    // setter injection
    // @Autowired(requried=false)
    // public void SetterMethod()


    //
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // option1:
    @RequestMapping(method={RequestMethod.POST, RequestMethod.PUT})
    //@RequestMapping(method={RequestMethod.GET, RequestMethod.HEAD})
    // get RequestBody-User
    // ServiceLayer ---> parse user, prepare for insertion/update
    // dao layer ----> insertion/update (difference)
    // option2:
    // @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", service.findAll());
        return "listUsers";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "newUser";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        service.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> userOpt = service.findById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "editUser";
        } else {
            return "redirect:/users";
        }
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        service.save(user);
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        Optional<User> userOpt = service.findById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "viewUser";
        } else {
            // handle not found, e.g. redirect to list or show error page
            return "redirect:/users";
        }
    }

   // option1:
  //@GetMapping("{groupId}/find/{userId}")
  //public UserDto findUserById(@PathVariable Long id) {


    // option2:
    // param is like: id=12, id=null, id="12", id=onetwo
    // agent: macOS, connection: alive, encoding: gzip, ..... (id=)
    //   @GetMapping("/find") // @RequestParam groupId, @RequestParam userId,


    // business requirement: if user provided id, return the user whose id= userId.
    //                       If user does not provide id, return all users

    // DTO -> Data Transfer Object
    // POJO, VO, DTO, ENTITY -----> JAVA Object
    // 1. return partial/mask data User -> SSN, credit card info, account balance, first name, last name, phone number ,email .....
    //    first name, last name, phone number
    // 2. Extra information --->
    // User -> SSN, credit card info, account balance,
    // first name, last name, phone number ,email .....
    // other those information, I need into include user's friends list ----> Friend table
    // Friends + User ----> UserDTO
    @GetMapping("/find")
    public ResponseEntity<UserDto> findUserById(@RequestParam Long id) {
        User user = service.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return new ResponseEntity<UserDto>(new UserDto(user.getName(), user.getEmail()),
                                            HttpStatus.OK);
        // return new UserDto(user.getName(), user.getEmail());
    }

}
