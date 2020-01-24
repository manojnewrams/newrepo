package com.example.h5api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@SuperBuilder
@Entity
@NoArgsConstructor
public class Nomination extends BaseEntity {
    @Column(name = "nominator_id")
    private int nominatorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserApp user;

    private String description;

    @ManyToOne
    @JoinColumn(name = "value_id")
    private Value value;

}
