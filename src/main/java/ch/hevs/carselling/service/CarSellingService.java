package ch.hevs.carselling.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import ch.hevs.carselling.entity.Car;
import ch.hevs.carselling.entity.CarBrand;
import ch.hevs.carselling.entity.CarStatus;
import ch.hevs.carselling.entity.Owner;
import ch.hevs.carselling.entity.Sale;
import ch.hevs.carselling.entity.SalesStats;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CarSellingService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<CarBrand> findAllBrands() {
        return em.createQuery("SELECT b FROM CarBrand b ORDER BY b.name", CarBrand.class)
                 .getResultList();
    }

    @Transactional
    public long countCars(Long brandId, BigDecimal maxPrice, Integer minYear, CarStatus status) {

        String jpql = """
            SELECT COUNT(c)
            FROM Car c
            WHERE (:brandId IS NULL OR c.brand.id = :brandId)
              AND (:maxPrice IS NULL OR c.price <= :maxPrice)
              AND (:minYear IS NULL OR c.year >= :minYear)
              AND (:status IS NULL OR c.status = :status)
            """;

        return em.createQuery(jpql, Long.class)
                 .setParameter("brandId", brandId)
                 .setParameter("maxPrice", maxPrice)
                 .setParameter("minYear", minYear)
                 .setParameter("status", status)
                 .getSingleResult();
    }

    @Transactional
    public List<Car> findCars(Long brandId, BigDecimal maxPrice, Integer minYear, CarStatus status,
                             int page, int pageSize) {

        String jpql = """
            SELECT c
            FROM Car c
            JOIN FETCH c.brand b
            JOIN FETCH c.owner o
            WHERE (:brandId IS NULL OR b.id = :brandId)
              AND (:maxPrice IS NULL OR c.price <= :maxPrice)
              AND (:minYear IS NULL OR c.year >= :minYear)
              AND (:status IS NULL OR c.status = :status)
            ORDER BY c.price ASC
            """;

        TypedQuery<Car> q = em.createQuery(jpql, Car.class)
                              .setParameter("brandId", brandId)
                              .setParameter("maxPrice", maxPrice)
                              .setParameter("minYear", minYear)
                              .setParameter("status", status);

        q.setFirstResult(page * pageSize);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }
    
    @Transactional
    public Car findCarById(Long id) {
        String jpql = """
            SELECT c
            FROM Car c
            JOIN FETCH c.brand
            JOIN FETCH c.owner
            WHERE c.id = :id
            """;

        return em.createQuery(jpql, Car.class)
                 .setParameter("id", id)
                 .getSingleResult();
    }
    
 // UC3 - create car with cascade persist
    @Transactional
    public void createCar(Car car) {
        em.persist(car);
    }

 // UC3
    @Transactional
    public CarBrand findBrandByName(String name) {
        try {
            return em.createQuery(
                    "SELECT b FROM CarBrand b WHERE b.name = :name",
                    CarBrand.class
            ).setParameter("name", name)
             .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

 // UC3
    @Transactional
    public Owner findOwnerById(Long ownerId) {
        if (ownerId == null) return null;
        return em.find(Owner.class, ownerId);
    }
    
 // UC3
    @Transactional
    public List<Owner> findAllOwners() {
        return em.createQuery(
                "SELECT o FROM Owner o ORDER BY o.lastName, o.firstName",
                Owner.class
        ).getResultList();
    }
    
 // UC4
    @Transactional
    public void sellCar(Long carId, String buyerName) {
        if (carId == null) {
            throw new IllegalArgumentException("carId is null");
        }

        Car car = em.find(Car.class, carId);
        if (car == null) {
            throw new IllegalArgumentException("Car not found: " + carId);
        }
        if (car.getStatus() == CarStatus.SOLD) {
            throw new IllegalStateException("Car already SOLD: " + carId);
        }

        // 1) status = SOLD
        car.setStatus(CarStatus.SOLD);

        // 2) create Sale
        Sale sale = new Sale();
        sale.setCar(car);
        sale.setSoldAt(LocalDateTime.now());
        sale.setPriceAtSale(car.getPrice());
        sale.setBuyerName((buyerName == null || buyerName.trim().isEmpty()) ? null : buyerName.trim());
        em.persist(sale);

        // 3) increment stats (create row lazily if missing)
        SalesStats stats = em.find(SalesStats.class, 1);
        if (stats == null) {
            stats = new SalesStats(1);
            em.persist(stats);
            em.flush(); 
        }
        stats.incrementSold();
    }
    
 // UC6
    @Transactional
    public void updateOwnerAddress(Long ownerId, String street, Integer zip, String city) {
        if (ownerId == null) {
            throw new IllegalArgumentException("ownerId is null");
        }

        Owner owner = em.find(Owner.class, ownerId);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found: " + ownerId);
        }

        if (owner.getAddress() == null) {
            owner.setAddress(new ch.hevs.carselling.entity.Address());
        }

        owner.getAddress().setStreet((street == null || street.trim().isEmpty()) ? null : street.trim());
        owner.getAddress().setZip(zip);
        owner.getAddress().setCity((city == null || city.trim().isEmpty()) ? null : city.trim());
    }
}
