package com.oleglunko.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dish extends NamedEntity {

    @NotNull
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User creator;

    public Dish(String name, int price) {
        super(name);
        this.price = price;
    }

    public Dish(Long id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}