package com.oleglunko.restaurantvoting.util;

import com.oleglunko.restaurantvoting.HasId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatonUtil {

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
}
