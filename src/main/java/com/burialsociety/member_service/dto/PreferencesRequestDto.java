package com.burialsociety.member_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class PreferencesRequestDto {
    private String preferredCommunicationMethod;
    private String preferredCommunicationForReminders;
    private List<String> languages;
    private List<String> hobbies;
}
