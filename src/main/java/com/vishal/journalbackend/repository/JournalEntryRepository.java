package com.vishal.journalbackend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vishal.journalbackend.entity.JournalEntry;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
