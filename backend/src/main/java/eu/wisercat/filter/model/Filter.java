package eu.wisercat.filter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "filter")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public Filter setId(Integer filterId) {
        this.id = filterId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Filter setName(String filterName) {
        this.name = filterName;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Filter setUser(User user) {
        this.user = user;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Filter setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean belongsTo(User user) {
        return this.user.equals(user);
    }
}
