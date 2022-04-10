package com.oleglunko.restaurantvoting.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class NamedDto extends BaseDto {

    @NotBlank
    @Size(min = 2, max = 128)
    protected String name;

    public NamedDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}
