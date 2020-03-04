package com.example.h5api.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@SuperBuilder
@Entity
public class Campaign extends GenericEntity {
    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    private String description;
    private boolean status;

    public Campaign() {
        this.status = false;
    }
}
