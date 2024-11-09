package com.tech.restaurant_reserve_pr.security.services;


import com.tech.restaurant_reserve_pr.model.User;
import com.tech.restaurant_reserve_pr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        // Pasa el objeto 'user' completo en lugar de solo el email
        return new UserDetailsImpl(user);  // Aquí se pasa el objeto 'user' completo
    }
}

