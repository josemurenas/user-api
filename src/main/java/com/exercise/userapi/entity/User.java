package com.exercise.userapi.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@Table(name = "app_user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Phone> phones = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modified;

    @CreationTimestamp
    private LocalDateTime lastLogin;

    private boolean isActive = true;

    private String token;

    public void setPhones(Set<Phone> phones) {
        this.phones.clear();
        Optional.ofNullable(phones).ifPresent(ps -> ps.forEach(this::addPhone));
    }

    public void addPhone(Phone phone){
        phone.setUser(this);
        this.phones.add(phone);
    }

}
