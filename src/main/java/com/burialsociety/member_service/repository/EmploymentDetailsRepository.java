package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.EmploymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentDetailsRepository extends JpaRepository<EmploymentDetails, Long> {
}
