package com.hive.hypermedia.web.error;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public final class Checks {

    public static final <T> T checkEntityExists(T entity, final String message) {
        if(entity == null){
            throw new EntityNotFoundException(message);
        }
        return entity;
    }
}
