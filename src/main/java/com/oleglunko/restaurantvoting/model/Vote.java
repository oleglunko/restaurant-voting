package com.oleglunko.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {

    @CreationTimestamp
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @NotNull
    @ManyToOne
    private Restaurant restaurant;

    public Vote(User creator, Restaurant restaurant) {
        this.creator = creator;
        this.restaurant = restaurant;
    }

    public Vote(Long id, LocalDate createdDate, User creator, Restaurant restaurant) {
        super(id);
        this.createdDate = createdDate;
        this.creator = creator;
        this.restaurant = restaurant;
    }
}
