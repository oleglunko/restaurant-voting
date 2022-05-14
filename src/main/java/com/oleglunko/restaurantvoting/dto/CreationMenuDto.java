package com.oleglunko.restaurantvoting.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class CreationMenuDto extends BaseDto {

    @NotNull
    LocalDate date;

    @NotEmpty
    List<Long> dishIds;

    public CreationMenuDto(Long id, LocalDate date, List<Long> dishIds) {
        super(id);
        this.date = date;
        this.dishIds = dishIds;
    }
}
