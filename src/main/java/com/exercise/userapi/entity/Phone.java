package com.exercise.userapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Phone {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String cityCode;

    @Column(nullable = false)
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}
