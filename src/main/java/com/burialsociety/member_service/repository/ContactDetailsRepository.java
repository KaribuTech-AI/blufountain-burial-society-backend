package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
}
