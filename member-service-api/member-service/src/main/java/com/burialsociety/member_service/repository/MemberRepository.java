package com.burialsociety.member_service.repository;


import com.burialsociety.member_service.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    // Spring Data JPA provides all CRUD methods.
    // You can add custom finders here later, e.g.:
    // Optional<Member> findByCaseNumber(String caseNumber);

}
