package com.vishal.journalbackend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.repository.UserRepository;

@SpringBootTest
class UserServiceTests {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserServiceTests(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // @Disabled
    @Test
    void testJournalEntries() {
        User user = userRepository.findByUsername("vishal");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    // @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "vishal",
            "p",
            "mohit"
    })
    void testFindByUsername(String username) {
        User user = userRepository.findByUsername(username);
        assertNotNull(user, "failed for: " + username);
    }

    // @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    void testSaveNewUser(User user) {
        assertTrue(userService.saveNewUser(user));
    }

}
