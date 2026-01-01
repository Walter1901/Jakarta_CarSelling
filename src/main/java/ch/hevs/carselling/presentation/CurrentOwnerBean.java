package ch.hevs.carselling.presentation;

import java.io.Serializable;

import ch.hevs.carselling.entity.Owner;
import ch.hevs.carselling.service.CarSellingService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("currentOwnerBean")
@SessionScoped
public class CurrentOwnerBean implements Serializable {

    @Inject
    private CarSellingService service;

    private Long selectedOwnerId;

    public Long getSelectedOwnerId() {
        return selectedOwnerId;
    }

    public void setSelectedOwnerId(Long selectedOwnerId) {
        this.selectedOwnerId = selectedOwnerId;
    }

    public Owner getSelectedOwner() {
        if (selectedOwnerId == null) return null;
        return service.findOwnerById(selectedOwnerId);
    }

    public boolean isOwnerSelected() {
        return selectedOwnerId != null;
    }

    public void clear() {
        selectedOwnerId = null;
    }
}
