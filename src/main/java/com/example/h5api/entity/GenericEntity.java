package com.example.h5api.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Data
@MappedSuperclass
@SuperBuilder
public class GenericEntity {

    public GenericEntity() {
        this.createAt = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Column(name = "update_at")
    @Temporal(TemporalType.DATE)
    private Date updateAt;

    @Column(name = "delete_at")
    @Temporal(TemporalType.DATE)
    private Date deleteAt;

}
