package com.oleglunko.restaurantvoting.dto;

import com.oleglunko.restaurantvoting.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseDto implements HasId {

    protected Long id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
