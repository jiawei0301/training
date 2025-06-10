package com.example.springpractice.controller;

import com.example.springpractice.dto.UserDto;
import com.example.springpractice.entity.User;
import com.example.springpractice.exception.UserNotFoundException;
import com.example.springpractice.service.UserService;
import org.hibernate.Remove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController // @RestController = @Controller + @ResponseBody
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
    //@RequestMapping(method={RequestMethod.POST, RequestMethod.PUT})
    //@RequestMapping(method={RequestMethod.GET, RequestMethod.HEAD})
    // get RequestBody-User
    // ServiceLayer ---> parse user, prepare for insertion/update
    // dao layer ----> insertion/update (difference)
    // option2:

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> dtos = service.findAll().stream()
                .map(u -> new UserDto(u.getName(), u.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET /users/{id} -- one user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User u = service.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserDto dto = new UserDto(u.getName(), u.getEmail());
        return ResponseEntity.ok(dto);
    }

    // POST /users
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        // map DTO to entity
        User toSave = new User();
        toSave.setName(userDto.getName());
        toSave.setEmail(userDto.getEmail());
        User saved = service.save(toSave);

        UserDto dto = new UserDto(saved.getName(), saved.getEmail());
        URI location = URI.create("/users/" + saved.getId());
        return ResponseEntity
                .created(location)
                .body(dto);
    }

    // PUT /users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto) {
        User existing = service.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setName(userDto.getName());
        existing.setEmail(userDto.getEmail());
        User updated = service.save(existing);

        UserDto dto = new UserDto(updated.getName(), updated.getEmail());
        return ResponseEntity.ok(dto);
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /*
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        service.save(user);
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
     */

}
