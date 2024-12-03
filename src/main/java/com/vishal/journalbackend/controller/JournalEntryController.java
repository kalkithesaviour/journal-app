package com.vishal.journalbackend.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.vishal.journalbackend.entity.User;
import com.vishal.journalbackend.service.JournalEntryService;
import com.vishal.journalbackend.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
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
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        try {
            journalEntryService.saveEntry(entry, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(entry);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction failed");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(
                    x -> x.getId().equals(objectId)).collect(Collectors.toList());
            if (!journalEntries.isEmpty()) {
                return ResponseEntity.ok(journalEntries.get(0));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found with id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid Id format: '" + id + "'.\nId should be a 24 characters hexString.");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            boolean removed = journalEntryService.deleteById(objectId, user);
            if (removed) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found with id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid Id format: '" + id + "'.\nId should be a 24 characters hexString.");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable String id,
            @RequestBody JournalEntry newEntry) {
        try {
            ObjectId objectId = new ObjectId(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            List<JournalEntry> journalEntries = user.getJournalEntries().stream().filter(
                    x -> x.getId().equals(objectId)).collect(Collectors.toList());
            if (!journalEntries.isEmpty()) {
                JournalEntry oldEntry = journalEntries.get(0);
                oldEntry.setTitle((newEntry.getTitle() != null &&
                        !newEntry.getTitle().isEmpty()) ? newEntry.getTitle()
                                : oldEntry.getTitle());
                oldEntry.setContent(
                        (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) ? newEntry.getContent()
                                : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return ResponseEntity.ok(oldEntry);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Journal entry not found with id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid Id format: '" + id + "'.\nId should be a 24 characters hexString.");
        }
    }

}
