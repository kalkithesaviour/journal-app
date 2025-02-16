package com.vishal.journalbackend.scheduler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserSchedulerTests {

    private final UserScheduler userScheduler;

    @Autowired
    public UserSchedulerTests(UserScheduler userScheduler) {
        this.userScheduler = userScheduler;
    }

    @Test
    void testFetchUsersAndSendSaMail() {
        assertDoesNotThrow(userScheduler::fetchUsersAndSendSaMail);
    }

}
