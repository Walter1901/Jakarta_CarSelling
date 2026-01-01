package ch.hevs.carselling.presentation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import ch.hevs.carselling.entity.Car;
import ch.hevs.carselling.entity.CarBrand;
import ch.hevs.carselling.entity.CarStatus;
import ch.hevs.carselling.service.CarSellingService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

//UC1

@Named
@ViewScoped
public class CarListBean implements Serializable {

    @Inject
    private CarSellingService service;

    private List<Car> cars;
    private List<CarBrand> brands;

    // filtres
    private Long brandId;
    private BigDecimal maxPrice;
    private Integer minYear;
    private CarStatus status; // ENUM

    // pagination
    private int page = 0;
    private int pageSize = 10;
    private long total;
    
    private String buyerName;    

    @PostConstruct
    public void init() {
        brands = service.findAllBrands();
        search();
    }

    public void search() {
        total = service.countCars(brandId, maxPrice, minYear, status);
        cars = service.findCars(brandId, maxPrice, minYear, status, page, pageSize);
    }

    public void searchFirstPage() {
        page = 0;
        search();
    }

    public void nextPage() {
        if ((page + 1) * pageSize < total) {
            page++;
            search();
        }
    }

    public void prevPage() {
        if (page > 0) {
            page--;
            search();
        }
    }

    // Pour <f:selectItems> : ComboList avec valeurs Enum
    public List<CarStatus> getStatuses() {
        return Arrays.asList(CarStatus.values());
    }
    
    public String sell(Long carId) {
        if (!FacesContext.getCurrentInstance().getExternalContext().isUserInRole("SELLER")
            && !FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMIN")) {
            return null;
        }
        service.sellCar(carId, null);
        search(); 
        return null;
    }

    // getters/setters
    public List<Car> getCars() { return cars; }
    public List<CarBrand> getBrands() { return brands; }

    public Long getBrandId() { return brandId; }
    public void setBrandId(Long brandId) { this.brandId = brandId; }

    public BigDecimal getMaxPrice() { return maxPrice; }
    public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }

    public Integer getMinYear() { return minYear; }
    public void setMinYear(Integer minYear) { this.minYear = minYear; }

    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }

    public int getPage() { return page; }
    public long getTotal() { return total; }
    public int getPageSize() { return pageSize; }
    
    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }
}
