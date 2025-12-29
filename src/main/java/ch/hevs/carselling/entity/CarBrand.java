package ch.hevs.carselling.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CAR_BRAND")
public class CarBrand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String name;

    public CarBrand() {}

    public CarBrand(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return name; }
}
