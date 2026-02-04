package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.AddressDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDetailsRepository extends JpaRepository<AddressDetails, Long> {
}
