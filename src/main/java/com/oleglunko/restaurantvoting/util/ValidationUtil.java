package com.oleglunko.restaurantvoting.util;

import com.oleglunko.restaurantvoting.HasId;
import com.oleglunko.restaurantvoting.util.exception.NotFoundException;
import lombok.experimental.UtilityClass;

import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {

    private final LocalTime CHECK_TIME = LocalTime.of(11, 0);

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
//            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, long id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
//            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static <T> T checkNotFoundWithId(T bean, long id) {
        if (bean != null) {
            return bean;
        } else {
            throw new NotFoundException("Not found entity with id = " + id);
        }
    }

    public static void checkIsLate() {
        if (LocalTime.now().isAfter(CHECK_TIME)) {
//            throw new TimeException("This is too late for voting");
        }
    }
}
