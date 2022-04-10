package com.oleglunko.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"creator", "menus", "dishes"})
@Table(name = "restaurant")
public class Restaurant extends NamedEntity {

    @NotBlank
    @Column(name = "address")
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant")
    private List<Dish> dishes;

    public Restaurant(Long id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}
