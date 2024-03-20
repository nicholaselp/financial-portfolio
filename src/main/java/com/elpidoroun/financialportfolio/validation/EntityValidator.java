package com.elpidoroun.financialportfolio.validation;

import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;

public interface EntityValidator<EntityT> {
    int TOP_PRIORITY_VALIDATION = 0;

    /**
     * The main validate method of an entity
     * @param original nullable field. Will be filled with the entity from DB only on update flow
     * @param entity the validatable entity. New entioty on create flow, changed entity on update flow,
     *                  existing entity on delete flow
     * @throws ValidationException
     **/

    Result<Nothing, String> validate(@Nullable EntityT original, @NonNull EntityT entity) throws ValidationException;

    default Result<Nothing, String> validate(@NonNull EntityT entity) throws ValidationException {
        return validate(null, entity);
    }

    int priority();
    String name();

    default boolean interruptValidationChain(@Nullable EntityT original, @NonNull EntityT entity){ return false; }
}
