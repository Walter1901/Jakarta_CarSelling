package ch.hevs.carselling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// UC7 — JPA Inheritance (FuelCar subclass)
// Inherits from Car with discriminator "FUEL".
// Specific field: tankLiters (fuel tank capacity in liters).

@Entity
@DiscriminatorValue("FUEL")
public class FuelCar extends Car {

    @Column(name = "TANK_LITERS")
    private Integer tankLiters;

    public FuelCar() {}

    public Integer getTankLiters() { return tankLiters; }
    public void setTankLiters(Integer tankLiters) { this.tankLiters = tankLiters; }
}
