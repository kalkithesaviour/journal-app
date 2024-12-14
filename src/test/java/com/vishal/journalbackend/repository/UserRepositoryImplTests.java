package com.vishal.journalbackend.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vishal.journalbackend.entity.User;

@SpringBootTest
class UserRepositoryImplTests {

    private final UserRepositoryImpl userRepositoryImpl;

    @Autowired
    public UserRepositoryImplTests(UserRepositoryImpl userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Disabled
    @Test
    void testGetUsersForSentimentAnalysis() {
        List<User> users = userRepositoryImpl.getUsersForSentimentAnalysis();
        assertTrue(!users.isEmpty());
    }

}
