/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misteam
 */
@Entity
@Table(name = "organization_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganizationLocation.findAll", query = "SELECT o FROM OrganizationLocation o"),
    @NamedQuery(name = "OrganizationLocation.findByOrganizationLocationId", query = "SELECT o FROM OrganizationLocation o WHERE o.organizationLocationId = :organizationLocationId"),
    @NamedQuery(name = "OrganizationLocation.findByName", query = "SELECT o FROM OrganizationLocation o WHERE o.name = :name")})
public class OrganizationLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "organization_location_id")
    @Id
    private BigInteger organizationLocationId;
    @Size(max = 100)
    @Column(name = "name")
    private String name;

    public OrganizationLocation() {
    }

    public BigInteger getOrganizationLocationId() {
        return organizationLocationId;
    }

    public void setOrganizationLocationId(BigInteger organizationLocationId) {
        this.organizationLocationId = organizationLocationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
