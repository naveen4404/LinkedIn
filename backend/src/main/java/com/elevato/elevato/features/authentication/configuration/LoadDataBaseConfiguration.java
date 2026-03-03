package com.elevato.elevato.features.authentication.configuration;


import com.elevato.elevato.features.authentication.model.AuthenticationUser;
import com.elevato.elevato.features.authentication.repository.AuthenticationUserRepository;
import com.elevato.elevato.features.authentication.utils.Encoder;
import com.elevato.elevato.features.feed.model.Post;
import com.elevato.elevato.features.feed.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Configuration
public class LoadDataBaseConfiguration {

    public final Encoder encoder;

    public LoadDataBaseConfiguration(Encoder encoder) {
        this.encoder = encoder;
    }


    @Bean
    public CommandLineRunner initDataBase(AuthenticationUserRepository authenticationUserRepository, PostRepository postRepository){
        AuthenticationUser user = new AuthenticationUser("naveen@gmail.com",encoder.encode("abc123"));

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                List<AuthenticationUser> users = createUsers(authenticationUserRepository);
                createPosts(postRepository, users);
            }
        };
    }

    private List<AuthenticationUser> createUsers(AuthenticationUserRepository authenticationUserRepository) {
        List<AuthenticationUser> users = List.of(
                createUser("naveen@example.com", "abc123", "Naveen", "Gundavarapu", "Software Engineer", "Docker Inc.", "San Francisco, CA",
                        "https://images.unsplash.com/photo-1633332755192-727a05c4013d?q=80&w=3560&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                createUser("kumar@example.com", "abc123", "Kumar", "Sharma", "HR Manager", "eToro", "Paris, Fr",
                        "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=3687&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                createUser("ram@example.com", "abc123", "Ram", "Kumar", "Product Manager", "Arc", "Dakar, SN",
                        "https://images.unsplash.com/photo-1640960543409-dbe56ccc30e2?q=80&w=2725&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                createUser("john@example.com", "abc123", "John", "Doe", "Software Engineer", "Orange", "Bordeaux, FR",
                        "https://images.unsplash.com/photo-1586297135537-94bc9ba060aa?q=80&w=3560&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"),
                createUser("hari@example.com", "abc123", "Hari", "Rao", "Data Scientist", "Zoho", "New Delhi, IN",
                        "https://images.unsplash.com/photo-1640951613773-54706e06851d?q=80&w=2967&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
        );

        authenticationUserRepository.saveAll(users);
        return users;
    }

    private AuthenticationUser createUser(String email, String password, String firstName, String lastName, String position,
                                          String company, String location, String profilePicture) {
        AuthenticationUser user = new AuthenticationUser(email, encoder.encode(password));
        user.setEmailVerified(true);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPosition(position);
        user.setCompany(company);
        user.setLocation(location);
        user.setProfilePicture(profilePicture);
        return user;
    }

    private void createPosts(PostRepository postRepository, List<AuthenticationUser> users) {
        Random random = new Random();
        for (int j = 1; j <= 10; j++) {
            Post post = new Post("Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                    users.get(random.nextInt(users.size())));
            post.setLikes(generateLikes(users, j, random));
            if (j == 1) {
                post.setPicture("https://images.unsplash.com/photo-1731176497854-f9ea4dd52eb6?q=80&w=3432&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            }
            postRepository.save(post);
        }
    }

    private HashSet<AuthenticationUser> generateLikes(List<AuthenticationUser> users, int postNumber, Random random) {
        HashSet<AuthenticationUser> likes = new HashSet<>();

        if (postNumber == 1) {
            while (likes.size() < 3) {
                likes.add(users.get(random.nextInt(users.size())));
            }
        } else {
            int likesCount = switch (postNumber % 5) {
                case 0 -> 3;
                case 2, 3 -> 2;
                default -> 1;
            };
            for (int i = 0; i < likesCount; i++) {
                likes.add(users.get(random.nextInt(users.size())));
            }
        }
        return likes;
    }

}
