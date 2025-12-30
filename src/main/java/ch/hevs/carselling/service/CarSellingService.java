package ch.hevs.carselling.service;

import java.math.BigDecimal;
import java.util.List;

import ch.hevs.carselling.entity.Car;
import ch.hevs.carselling.entity.CarBrand;
import ch.hevs.carselling.entity.CarStatus;
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
}
