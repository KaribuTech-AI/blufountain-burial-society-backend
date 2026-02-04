package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
}
