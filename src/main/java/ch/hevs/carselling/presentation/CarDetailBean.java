package ch.hevs.carselling.presentation;

import java.io.Serializable;

import ch.hevs.carselling.entity.Car;
import ch.hevs.carselling.service.CarSellingService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.NoResultException;

//UC2

@Named
@ViewScoped
public class CarDetailBean implements Serializable {

    @Inject
    private CarSellingService service;

    private Long id;
    private Car car;

    public void load() {
        if (id == null) {
            car = null;
            return;
        }
        try {
            car = service.findCarById(id);
        } catch (NoResultException e) {
            car = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }
}
