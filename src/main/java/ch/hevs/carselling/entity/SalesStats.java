package ch.hevs.carselling.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "SALES_STATS")
public class SalesStats implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TOTAL_SOLD", nullable = false)
    private long totalSold;

    @Version
    @Column(name = "VERSION", nullable = false)
    private long version;

    public SalesStats() { }

    public SalesStats(Integer id) {
        this.id = id;
        this.totalSold = 0;
        this.version = 0;
    }

    public void incrementSold() {
        this.totalSold++;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public long getTotalSold() { return totalSold; }
    public void setTotalSold(long totalSold) { this.totalSold = totalSold; }

    public long getVersion() { return version; }
    public void setVersion(long version) { this.version = version; }
}
