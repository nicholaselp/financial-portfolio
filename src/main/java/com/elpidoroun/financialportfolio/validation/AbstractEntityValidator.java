package com.elpidoroun.financialportfolio.validation;

import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.ValidationException;

import static java.util.Objects.requireNonNull;

public abstract class AbstractEntityValidator<EntityT> implements EntityValidator<EntityT> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractEntityValidator.class);
    private final int priority;

    protected AbstractEntityValidator(int priority){ this.priority = priority; }
    @Override
    public Result<Nothing, String> validate(EntityT original, EntityT entity) throws ValidationException {
        requireNonNull(entity, "entity to validate is missing");

        if(entityToValidate(original, entity)){
            logger.info("Validate with {}", this.getClass().getSimpleName());
            return doValidate(original, entity);
        }
        return Result.success();
    }

    @Override
    public int priority() { return priority; }

    @Override
    public String name() { return getClass().getSimpleName(); }

    protected abstract boolean entityToValidate(@Nullable EntityT original, @NonNull EntityT entity);
    protected abstract Result<Nothing, String> doValidate(@Nullable EntityT original, @NonNull EntityT entityT);
}
