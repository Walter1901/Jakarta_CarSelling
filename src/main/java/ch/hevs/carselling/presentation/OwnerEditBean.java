package ch.hevs.carselling.presentation;

import ch.hevs.carselling.entity.Owner;
import ch.hevs.carselling.service.CarSellingService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("ownerEditBean")
@ViewScoped
public class OwnerEditBean implements Serializable {

    @Inject
    private CurrentOwnerBean currentOwnerBean;

    @Inject
    private CarSellingService service;

    private String street;
    private Integer zip;
    private String city;

    @PostConstruct
    public void init() {
        Owner owner = currentOwnerBean.getSelectedOwner();
        if (owner != null && owner.getAddress() != null) {
            street = owner.getAddress().getStreet();
            zip = owner.getAddress().getZip();
            city = owner.getAddress().getCity();
        }
    }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public Integer getZip() { return zip; }
    public void setZip(Integer zip) { this.zip = zip; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String save() {
        if (!currentOwnerBean.isOwnerSelected()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "No owner selected", null));
            return null;
        }

        service.updateOwnerAddress(currentOwnerBean.getSelectedOwnerId(), street, zip, city);

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Address saved", null));

        return "ownerSelect?faces-redirect=true";
    }
}
