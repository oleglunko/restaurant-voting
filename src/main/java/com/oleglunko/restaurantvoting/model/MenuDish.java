package com.oleglunko.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_dish")
public class MenuDish extends BaseEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    public MenuDish(Long id, Menu menu, Dish dish) {
        super(id);
        this.menu = menu;
        this.dish = dish;
    }
}
