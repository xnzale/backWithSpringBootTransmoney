package com.front.transmoney.controller;


import com.front.transmoney.model.Message;
import com.front.transmoney.model.PartForm;
import com.front.transmoney.model.Role;
import com.front.transmoney.model.User;
import com.front.transmoney.repository.UserRepository;
import com.front.transmoney.services.UserDetailsServiceImpl;
import com.front.transmoney.services.UserService;
import com.front.transmoney.services.UserDetailsServiceImpl;
import com.front.transmoney.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class SecuityController {
    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
    public User register(@RequestBody PartForm userForm) throws Exception {

        User user=new  User(userForm.getNom(),userForm.getPrenom(), userForm.getEtat(), userForm.getTelephone(), userForm.getImageName(),userForm.getUsername(),userForm.getEmail(),userForm.getPassword());

            user.setPassword (encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role=new Role();
        role.setId(userForm.getRol());
        roles.add(role);
        user.setRoles(roles);
        return userService.save(user);
    }



    @PreAuthorize("hasAuthority('ROLE_SUPER') && hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> findAll(){
        User user = userDetailsService.getUserconnected();
        return userService.findAll();
    }
    @PreAuthorize("hasAuthority('ROLE_SUPER') or hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/userCon",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User find(){
        User user = userDetailsService.getUserconnected();
        return user;
    }
    @PreAuthorize("hasAuthority('ROLE_SUPER') or hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/etat/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Message bloquer(@PathVariable("id") long id) throws Exception{
        System.out.println(id);

            User user = userService.findById(id).orElseThrow(
                    () -> new Exception("utilisateur introuvable")
            );

            if (user != null) {
                if(user.getId()==(1)){
                    Message message = new Message(200,"Impossible de bloquer cet utilisateur");
                    return message;
                }
                if(user.getEtat().equals("bloquer")){
                    user.setEtat("actif");
                    userService.save(user);
                    Message message = new Message(200,"l' utilisateur est actif");
                    return message;
                }
                 else if(user.getEtat().equals("actif")){
                    user.setEtat("bloquer");
                    userService.save(user);
                    Message message = new Message(200,"l' utilisateur est bloqué");
                    return message;

                }



            }


        Message message = new Message(200,"erreur");
        return message;
}



}
