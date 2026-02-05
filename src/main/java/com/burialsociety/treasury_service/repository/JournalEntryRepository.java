package com.burialsociety.treasury_service.repository;

import com.burialsociety.treasury_service.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findAllByOrderByEntryDateDesc();
}
