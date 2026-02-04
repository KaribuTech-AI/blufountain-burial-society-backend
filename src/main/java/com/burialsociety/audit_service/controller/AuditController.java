package com.burialsociety.audit_service.controller;

import com.burialsociety.audit_service.entity.AuditLog;
import com.burialsociety.audit_service.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public ResponseEntity<List<AuditLog>> getLogs() {
        return ResponseEntity.ok(auditService.getAllLogs());
    }
}
