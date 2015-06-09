package com.geoinfo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Periodo implements Serializable{
    @Id
    @Column(length = 20)
    private String cdPeriodo;

    public String getCdPeriodo() {
        return cdPeriodo;
    }

    public void setCdPeriodo(String cdPeriodo) {
        this.cdPeriodo = cdPeriodo;
    }
    
}
