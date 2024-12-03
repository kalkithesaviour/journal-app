package com.vishal.journalbackend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Disabled
    public void testJournalEntries() {
        User user = userRepository.findByUsername("vishal");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @Disabled
    @ValueSource(strings = {
            "vishal",
            "p",
            "mohit"
    })
    public void testFindByUsername(String username) {
        User user = userRepository.findByUsername(username);
        assertNotNull(user, "failed for: " + username);
    }

    @ParameterizedTest
    @Disabled
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user, Arrays.asList("USER")));
    }

}
