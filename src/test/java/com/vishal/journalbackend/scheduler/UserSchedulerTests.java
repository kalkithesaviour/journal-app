package com.vishal.journalbackend.scheduler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserSchedulerTests {

    private final UserScheduler userScheduler;

    public UserSchedulerTests(UserScheduler userScheduler) {
        this.userScheduler = userScheduler;
    }

    @Test
    void testFetchUsersAndSendSaMail() {
        assertDoesNotThrow(userScheduler::fetchUsersAndSendSaMail);
    }

}
