package ch.hevs.carselling.presentation;

import ch.hevs.carselling.entity.Car;
import ch.hevs.carselling.service.CarSellingService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class CarListBean {

    @Inject
    private CarSellingService service;

    private List<Car> cars;

    @PostConstruct
    public void init() {
        service.initDemoDataIfEmpty();
        cars = service.findAllCars();
    }

    public List<Car> getCars() {
        return cars;
    }
}
