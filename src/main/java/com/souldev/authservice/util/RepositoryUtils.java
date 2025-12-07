package com.souldev.authservice.util;

import com.souldev.authservice.exception.ConflictException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtils {  //this class and method can be used in every save and update for every entity so we DRY(don't repeat yourself)

    public <T> T saveWithConflictCheck(JpaRepository<T, Long> repository, T entity, String ConflictMessage) {
        try {
            return repository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException(ConflictMessage);
        }
    }
}
