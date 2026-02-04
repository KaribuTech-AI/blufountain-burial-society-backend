package com.burialsociety.claims_service.repository;

import com.burialsociety.claims_service.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByMemberId(Long memberId);
    List<Claim> findByStatus(String status);
}
