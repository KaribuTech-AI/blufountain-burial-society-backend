package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.Citizenship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenshipRepository extends JpaRepository<Citizenship, Long> {
}
