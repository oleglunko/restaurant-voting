package com.oleglunko.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dish")
public class Dish extends NamedEntity {

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Dish(Long id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
