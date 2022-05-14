package com.oleglunko.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "restaurant")
public class Restaurant extends NamedEntity {

    @NotBlank
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "opened_at", nullable = false)
    private LocalTime openedAt;

    @NotNull
    @Column(name = "closed_at")
    private LocalTime closedAt;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonIgnore
    private User creator;

    public Restaurant(Long id, String name, String address) {
        super(id, name);
        this.address = address;
    }
}
