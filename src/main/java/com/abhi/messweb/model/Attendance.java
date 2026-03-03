package com.abhi.messweb.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private boolean breakfast;
    private boolean lunch;
    private boolean dinner;

    @ManyToOne
    private Member member;

    // getters & setters
}