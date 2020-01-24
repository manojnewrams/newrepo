package com.example.h5api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Winner extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "value_id")
    private Value value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp user;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    private int count;
}
