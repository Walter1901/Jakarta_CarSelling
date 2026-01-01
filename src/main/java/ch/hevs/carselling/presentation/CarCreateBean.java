package ch.hevs.carselling.presentation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import ch.hevs.carselling.entity.*;
import ch.hevs.carselling.service.CarSellingService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

// UC3 (updated for UC7 inheritance)
@Named("carCreateBean")
@ViewScoped
public class CarCreateBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CarSellingService service;

    private String model;
    private Integer year;
    private BigDecimal price;
    private CarStatus status = CarStatus.AVAILABLE;
    private String brandName;

    private Long ownerId;

    // UC7: choose subtype
    private String carType = "FUEL";

    public List<CarStatus> getStatuses() {
        return Arrays.asList(CarStatus.values());
    }

    public List<Owner> getOwners() {
        return service.findAllOwners();
    }

    public List<String> getCarTypes() {
        return Arrays.asList("FUEL", "ELECTRIC");
    }

    public String create() {
        CarBrand brand = service.findBrandByName(brandName);
        if (brand == null) {
            brand = new CarBrand();
            brand.setName(brandName);
        }

        Owner owner = service.findOwnerById(ownerId);
        if (owner == null) {
            return null;
        }

        // UC7: instantiate concrete class
        Car car;
        if ("ELECTRIC".equalsIgnoreCase(carType)) {
            car = new ElectricCar();
        } else {
            car = new FuelCar();
        }

        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        car.setStatus(status);
        car.setBrand(brand);
        car.setOwner(owner);

        service.createCar(car);

        return "carList?faces-redirect=true";
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public String getCarType() { return carType; }
    public void setCarType(String carType) { this.carType = carType; }
}
