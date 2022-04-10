package com.oleglunko.restaurantvoting.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantDto extends NamedDto {

    @NotBlank
    String address;

    @NotNull
    LocalTime openedAt;

    @NotNull
    LocalTime closedAt;

    public RestaurantDto(Long id, String name, String address, LocalTime openedAt, LocalTime closedAt) {
        super(id, name);
        this.address = address;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
    }
}
