package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {
}
