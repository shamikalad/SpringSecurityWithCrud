package com.example.springsecuritycruddemo.controller;

import com.example.springsecuritycruddemo.model.User;
import com.example.springsecuritycruddemo.model.dtos.JwtRequest;
import com.example.springsecuritycruddemo.model.dtos.JwtResponse;
import com.example.springsecuritycruddemo.repository.UserRepository;
import com.example.springsecuritycruddemo.services.UserService;
import com.example.springsecuritycruddemo.services.jwt.CustomerServiceImpl;
import com.example.springsecuritycruddemo.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
@CrossOrigin("*")
public class JwtAuthenticationController {
    @Autowired
   public UserService userService;
    @Autowired
   public UserRepository userRepository;
    @Autowired
   public AuthenticationManager authenticationManager;
    @Autowired
   public CustomerServiceImpl customerService;
    @Autowired
   public JwtUtil jwtUtil;


    @PostMapping("/create-normal-user")
    public User createNormalUser(@RequestBody User user)
    {
        user.setEnabled(Boolean.TRUE);
        user.setRole("NORMAL_USER");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.createUser(user);

    }

    @PostConstruct
    public void createAdmin(){
        User user=new User();
        user.setId(1L);
        user.setUsername("admin@gmail.com");
        user.setRole("SUPER_ADMIN");
        user.setEnabled(Boolean.TRUE);
        user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
        userService.createUser(user);


    }

    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password.");
        }

        UserDetails userDetails = customerService.loadUserByUsername(request.getUsername());
        //optional provides isEmpty(),isPresent() methods
        Optional<User> optionalCustomer = userRepository.getUserByUsername(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        JwtResponse response = new JwtResponse();
        if(optionalCustomer.isPresent()){
            response.setToken(jwt);
            response.setRole(optionalCustomer.get().getRole());
            response.setUsername(optionalCustomer.get().getUsername());
            return response;
        }
        return null;
    }



    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable("id") Long id)
    {

        userService.deleteUser(id);
    }

    @GetMapping("/get-user-by-id/{id}")
    public User getUserById(@PathVariable("id") Long id)
    {
        return userService.getUserById(id);
    }

    @GetMapping("/get-all-users")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }


    @PostMapping("create-user-multipart")
    public User createUserWithMultipart(@RequestParam("username") String username,
                                              @RequestParam("password") String password,
                                        @RequestParam("userProfile") MultipartFile userProfile) throws IOException {
        User user=new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole("NORMAL_USER");
        user.setEnabled(Boolean.TRUE);
        user.setUserProfile(userProfile.getBytes());
        return userService.createUser(user);
    }

    @PutMapping("update-user-multipart")
    public User updateUserWithMultipart(@RequestParam("id") Long id,
                                        @RequestParam("username") String username,
                                        @RequestParam("password") String password,
                                        @RequestParam("userProfile") MultipartFile userProfile) throws IOException {
        User user=new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole("NORMAL_USER");
        user.setEnabled(Boolean.TRUE);
        user.setUserProfile(userProfile.getBytes());
        return userService.createUser(user);
    }

}
