package ch.hevs.carselling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

// UC6 — Embedded address (JPA Embedding)
// @Embeddable: non-entity component integrated into Owner.
// No separate table: columns (street, zip, city) in OWNER table.

@Embeddable
public class Address implements Serializable {

    @Column(name = "STREET", length = 120)
    private String street;

    @Column(name = "ZIP")
    private Integer zip; // Int can be NULL

    @Column(name = "CITY", length = 80)
    private String city;

    public Address() {}

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public Integer getZip() { return zip; }
    public void setZip(Integer zip) { this.zip = zip; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}
