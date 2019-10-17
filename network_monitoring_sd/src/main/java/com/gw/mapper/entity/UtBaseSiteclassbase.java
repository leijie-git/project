package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Base_SiteClassBase")
public class UtBaseSiteclassbase implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SiteClassName")
    private String siteclassname;

    @Column(name = "SiteClassDesc")
    private String siteclassdesc;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return SiteClassName
     */
    public String getSiteclassname() {
        return siteclassname;
    }

    /**
     * @param siteclassname
     */
    public void setSiteclassname(String siteclassname) {
        this.siteclassname = siteclassname;
    }

    /**
     * @return SiteClassDesc
     */
    public String getSiteclassdesc() {
        return siteclassdesc;
    }

    /**
     * @param siteclassdesc
     */
    public void setSiteclassdesc(String siteclassdesc) {
        this.siteclassdesc = siteclassdesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UtBaseSiteclassbase)) return false;

        UtBaseSiteclassbase that = (UtBaseSiteclassbase) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (siteclassname != null ? !siteclassname.equals(that.siteclassname) : that.siteclassname != null)
            return false;
        return !(siteclassdesc != null ? !siteclassdesc.equals(that.siteclassdesc) : that.siteclassdesc != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (siteclassname != null ? siteclassname.hashCode() : 0);
        result = 31 * result + (siteclassdesc != null ? siteclassdesc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UtBaseSiteclassbase{" +
                "id=" + id +
                ", siteclassname='" + siteclassname + '\'' +
                ", siteclassdesc='" + siteclassdesc + '\'' +
                '}';
    }
}