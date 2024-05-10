package com.elpidoroun.financialportfolio.security.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test
    public void success_save(){
        var user = userRepository.save(User.builder()
                .withEmail("dummy@email.com")
                .withFullName("Dummy Dummy")
                .withRole(Role.USER)
                .withPassword("dummyPassword").build());

        assertThat(userRepository.findAll()).hasSize(3) // contains 3 because liquibase inserted 2 users already
                .contains(user);

    }

    @Test
    public void success_findByEmail(){
        var user = userRepository.save(User.builder()
                .withEmail("dummy@email.com")
                .withFullName("Dummy Dummy")
                .withRole(Role.USER)
                .withPassword("dummyPassword").build());

        assertThat(userRepository.findByEmail(user.getEmail())).isPresent().hasValueSatisfying(storedUser -> {
            assertThat(storedUser.getId()).isEqualTo(user.getId());
            assertThat(storedUser.getFullName()).isEqualTo(user.getFullName());
            assertThat(storedUser.getEmail()).isEqualTo(user.getEmail());
            assertThat(storedUser.getPassword()).isEqualTo(user.getPassword());
        });
    }

    @Test
    public void fail_findByEmail_empty(){
        assertThat(userRepository.findByEmail("unknown_email")).isNotPresent();
    }

    //save
    //findByEmail
}
