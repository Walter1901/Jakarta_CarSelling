package ch.hevs.carselling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

// UC7 — JPA Inheritance (ElectricCar subclass)
// Inherits from Car with discriminator "ELECTRIC".
// Specific field: batteryKwh (battery capacity in kWh).

@Entity
@DiscriminatorValue("ELECTRIC")
public class ElectricCar extends Car {

    @Column(name = "BATTERY_KWH")
    private Integer batteryKwh;

    public ElectricCar() {}

    public Integer getBatteryKwh() { return batteryKwh; }
    public void setBatteryKwh(Integer batteryKwh) { this.batteryKwh = batteryKwh; }
}
