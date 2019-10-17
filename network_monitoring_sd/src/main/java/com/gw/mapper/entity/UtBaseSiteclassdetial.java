package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Table(name = "UT_Base_SiteClassDetial")
@Data
public class UtBaseSiteclassdetial implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CheckInfo")
    private String checkinfo;

    @Column(name = "CheckMethod")
    private String checkmethod;

    @Column(name = "SiteClassID")
    private Long siteclassid;

    @Column(name = "unitId")
    private Long unitId;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtBaseSiteclassdetial that = (UtBaseSiteclassdetial) o;
        return Objects.equals(checkinfo, that.checkinfo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(checkinfo);
    }
}