package com.springboot.springit.bootstrap;

import com.springboot.springit.domain.Comment;
import com.springboot.springit.domain.Link;
import com.springboot.springit.domain.Role;
import com.springboot.springit.domain.User;
import com.springboot.springit.repository.CommentRepository;
import com.springboot.springit.repository.LinkRepository;
import com.springboot.springit.repository.RoleRepository;
import com.springboot.springit.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private Map<String,User> users = new HashMap<>();
    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository,
                          UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) {

        addUsersAndRoles();

        Map<String,String> links = new HashMap<>();
        links.put("Totems of Understanding","https://shaileshdagar.medium.com/totems-of-understanding-8c3ec5a06ba2");
        links.put("The Metamorphic Nature of Conversations","https://shaileshdagar.medium.com/the-metamorphic-nature-of-conversations-4646c91a539e");
        links.put("The Performative Nature of Social Media","https://shaileshdagar.medium.com/the-performative-nature-of-social-media-648e9e4184a6");
        links.put("LOVE DEATH + ROBOTS","https://shaileshdagar.medium.com/love-death-robots-deee1b218f17");
        links.put("Philosophy is for the lazy","https://shaileshdagar.medium.com/philosophy-is-for-the-lazy-3c7e4905fe8a");
        links.put("Metaverse inside our head","https://shaileshdagar.medium.com/metaverse-inside-our-head-297f09b128a7");
        links.put("Verbal Jiu Jitsu of a Dialogue","https://shaileshdagar.medium.com/verbal-jiu-jitsu-of-a-dialogue-73ea3f96f0a");
        links.put("The Individual that Never Existed","https://shaileshdagar.medium.com/the-individual-that-never-existed-ad7c869096a2");
        links.put("Stories of our lives","https://shaileshdagar.medium.com/stories-of-our-lives-b64e392e59de");
        links.put("The Paradox that is Free Will","https://shaileshdagar.medium.com/the-paradox-that-is-free-will-ec73fe3b5e06");
        links.put("We all live in America","https://shaileshdagar.medium.com/we-all-live-in-america-ac1d621f105e");

        links.forEach((k,v) -> {
            User u1 = users.get("user@gmail.com");
            User u2 = users.get("super@gmail.com");
            Link link = new Link(k,v);
            if(k.startsWith("Build")) {
                link.setUser(u1);
            } else {
                link.setUser(u2);
            }
            linkRepository.save(link);
            Comment spring = new Comment("This is some philosophical shit!",link);
            Comment security = new Comment("Are you really as pretentious in real life or just here?",link);
            Comment pwa = new Comment("I want to smoke what this guy smokes.",link);
            List<Comment> comments = Arrays.asList(spring,security,pwa);
            for(Comment comment : comments) {
                commentRepository.save(comment);
                link.addComment(comment);
            }
        });

        long linkCount = linkRepository.count();
        System.out.println("Number of links in the database: " + linkCount );
    }

    private void addUsersAndRoles() {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = bCryptPasswordEncoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com",secret,true,"Joe","User","joedirt");
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        userRepository.save(user);
        users.put("user@gmail.com",user);

        User admin = new User("admin@gmail.com",secret,true,"Joe","Admin","masteradmin");
        admin.setAlias("joeadmin");
        admin.addRole(adminRole);
        admin.setConfirmPassword(secret);
        userRepository.save(admin);
        users.put("admin@gmail.com",admin);

        User master = new User("super@gmail.com",secret,true,"Super","User","superduper");
        master.addRoles(new HashSet<>(Arrays.asList(userRole,adminRole)));
        master.setConfirmPassword(secret);
        userRepository.save(master);
        users.put("super@gmail.com",master);
    }
}
