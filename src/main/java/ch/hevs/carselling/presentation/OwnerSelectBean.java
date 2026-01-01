package ch.hevs.carselling.presentation;

import java.util.List;

import ch.hevs.carselling.entity.Owner;
import ch.hevs.carselling.service.CarSellingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("ownerSelectBean")
@RequestScoped
public class OwnerSelectBean {

    @Inject
    private CarSellingService service;

    @Inject
    private CurrentOwnerBean currentOwnerBean;

    private Long ownerId;

    public List<Owner> getOwners() {
        return service.findAllOwners();
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String selectOwner() {
        currentOwnerBean.setSelectedOwnerId(ownerId);
        return "carList?faces-redirect=true";
    }

    public String clearOwner() {
        currentOwnerBean.clear();
        return "carList?faces-redirect=true";
    }
}
