package com.vishal.journalbackend.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.journalbackend.entity.JournalEntry;
import com.vishal.journalbackend.entity.JournalEntryDTO;
import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.service.JournalEntryService;
import com.vishal.journalbackend.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private static final String JOURNAL_ENTRY_NOT_FOUND = "Journal entry not found with id: ";
    private static final String INVALID_ID_FORMAT_PREFIX = "Invalid Id format: '";
    private static final String INVALID_ID_FORMAT_SUFFIX = "'.\nId should be a 24 characters hexString.";

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> allEntry = user.getJournalEntries();
        if (allEntry != null && !allEntry.isEmpty()) {
            return ResponseEntity.ok(allEntry);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createEntry(@RequestBody JournalEntryDTO entryDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        JournalEntry entry = new JournalEntry();
        entry.setTitle(entryDTO.getTitle());
        entry.setContent(entryDTO.getContent());

        try {
            journalEntryService.saveEntry(entry, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction failed");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getJournalEntryById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(
                    x -> x.getId().equals(objectId)).toList();
            if (!journalEntries.isEmpty()) {
                return ResponseEntity.ok(journalEntries.get(0));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JOURNAL_ENTRY_NOT_FOUND + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(INVALID_ID_FORMAT_PREFIX + id + INVALID_ID_FORMAT_SUFFIX);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Object> deleteJournalEntryById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            boolean removed = journalEntryService.deleteById(objectId, user);
            if (removed) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JOURNAL_ENTRY_NOT_FOUND + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(INVALID_ID_FORMAT_PREFIX + id + INVALID_ID_FORMAT_SUFFIX);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Object> updateJournalEntryById(@PathVariable String id,
            @RequestBody JournalEntryDTO newEntryDTO) {
        try {
            ObjectId objectId = new ObjectId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(
                    x -> x.getId().equals(objectId)).toList();
            if (!journalEntries.isEmpty()) {
                JournalEntry oldEntry = journalEntries.get(0);

                JournalEntry newEntry = new JournalEntry();
                newEntry.setTitle(newEntryDTO.getTitle());
                newEntry.setContent(newEntryDTO.getContent());

                oldEntry.setTitle((newEntry.getTitle() != null &&
                        !newEntry.getTitle().isEmpty()) ? newEntry.getTitle()
                                : oldEntry.getTitle());
                oldEntry.setContent(
                        (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) ? newEntry.getContent()
                                : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return ResponseEntity.ok(oldEntry);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(JOURNAL_ENTRY_NOT_FOUND + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(INVALID_ID_FORMAT_PREFIX + id + INVALID_ID_FORMAT_SUFFIX);
        }
    }

}
