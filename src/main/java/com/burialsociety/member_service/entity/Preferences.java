package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "member_preferences", schema = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "preferred_communication_method")
    private String preferredCommunicationMethod;

    @Column(name = "preferred_communication_for_reminders")
    private String preferredCommunicationForReminders;

    @ElementCollection
    @CollectionTable(name = "member_languages", schema = "member", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "language")
    private List<String> languages;

    @ElementCollection
    @CollectionTable(name = "member_hobbies", schema = "member", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "hobby")
    private List<String> hobbies;

}
