package com.vishal.journalbackend.exception;

public class JournalEntryException extends RuntimeException {

    public JournalEntryException(String message) {
        super(message);
    }

    public JournalEntryException(String message, Throwable cause) {
        super(message, cause);
    }

}
