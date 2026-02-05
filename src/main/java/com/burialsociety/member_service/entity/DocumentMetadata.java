package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "document_metadata", schema = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "document_type")
    private String documentType; // ID Document, Proof of Residence

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath; // Or storage URL

    @Lob
    @Column(name = "content_base64")
    private String contentBase64; // If storing small documents directly in DB temporarily

    @Column(name = "upload_date")
    private OffsetDateTime uploadDate;
}
