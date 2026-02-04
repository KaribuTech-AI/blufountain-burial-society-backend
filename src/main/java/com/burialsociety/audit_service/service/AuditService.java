package com.burialsociety.audit_service.service;

import com.burialsociety.audit_service.entity.AuditLog;
import com.burialsociety.audit_service.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditLogRepository auditLogRepository;

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    public AuditLog logAction(String user, String action, String entity, String changes) {
        AuditLog log = AuditLog.builder()
                .timestamp(LocalDateTime.now())
                .username(user)
                .action(action)
                .entityName(entity)
                .changes(changes)
                .build();
        return auditLogRepository.save(log);
    }
}
