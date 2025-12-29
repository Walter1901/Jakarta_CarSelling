package ch.hevs.carselling.service;

import ch.hevs.carselling.entity.Car;
import ch.hevs.carselling.entity.CarBrand;
import ch.hevs.carselling.entity.Owner;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class CarSellingService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void initDemoDataIfEmpty() {
        Long count = em.createQuery("select count(c) from Car c", Long.class).getSingleResult();
        if (count != null && count > 0) return;

        Owner o1 = new Owner("Alice", "Martin", "alice.martin@example.com");
        Owner o2 = new Owner("Bob", "Durand", "bob.durand@example.com");

        CarBrand bmw = new CarBrand("BMW");
        CarBrand audi = new CarBrand("Audi");

        em.persist(o1);
        em.persist(o2);
        em.persist(bmw);
        em.persist(audi);

        em.persist(new Car("320d", 2019, new BigDecimal("23900.00"), bmw, o1));
        em.persist(new Car("A3", 2018, new BigDecimal("18900.00"), audi, o2));
    }

    public List<Car> findAllCars() {
        return em.createQuery(
                "select c from Car c join fetch c.brand join fetch c.owner order by c.id",
                Car.class
        ).getResultList();
    }
}
