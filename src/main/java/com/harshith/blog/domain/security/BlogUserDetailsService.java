package com.harshith.blog.domain.security;

import com.harshith.blog.domain.Repository.UserRepository;
import com.harshith.blog.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (User) userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user Not found"));
        return new BlogUsersDetails(user);
    }
}
