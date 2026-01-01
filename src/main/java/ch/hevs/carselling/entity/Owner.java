package ch.hevs.carselling.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OWNER")
public class Owner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 80)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 80)
    private String lastName;

    @Column(name = "EMAIL", length = 120)
    private String email;

    @Embedded
    private Address address = new Address();

    public Owner() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) {
        this.address = (address == null) ? new Address() : address;
    }
}
