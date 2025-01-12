package com.vishal.journalbackend.scheduler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Disabled;
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

    @Disabled
    @Test
    void testFetchUsersAndSendSaMail() {
        assertDoesNotThrow(userScheduler::fetchUsersAndSendSaMail);
    }

}
