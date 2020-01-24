package com.example.h5api.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@SuperBuilder
@Entity
public class Campaign extends BaseEntity {
    @Column(name = "date_from")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;

    @Column(name = "date_to")
    @Temporal(TemporalType.DATE)
    private Date dateTo;
    
    private String description;
    private boolean status; //true = active campaign

    public Campaign (){
        this.status = false;
    }
}
