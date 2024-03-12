package com.elpidoroun.financialportfolio.service;

import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import com.elpidoroun.financialportfolio.validation.EntityValidator;
import com.elpidoroun.financialportfolio.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.elpidoroun.financialportfolio.validation.ErrorType.SYSTEM;
import static java.util.Objects.requireNonNull;

@Service
public class ValidationService<EntityT> {
    private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    private final List<EntityValidator<EntityT>> validators;

    public ValidationService(List<EntityValidator<EntityT>> validators){
        this.validators = requireNonNull(validators).stream()
                .sorted(Comparator.comparing(EntityValidator::priority))
                .peek(checker -> logger.info("Registered validator : {}, priority: {}", checker.name(), checker.priority()))
                .toList();
    }

    public Result<Nothing, List<ValidationError>> validate(@Nullable EntityT original, @NonNull EntityT entity){
        requireNonNull(entity, "Nothing to validate");

        List<ValidationError> errors = new ArrayList<>();

        try {
            for (EntityValidator<EntityT> checker : validators){
                var result = checker.validate(original, entity);
                if(result.isFail()){
                    errors.add(result.getError().orElseThrow(() -> new IllegalStateException("Unexpected exception during validation")));
                }
                if(checker.interruptValidationChain(original, entity)){
                    logger.info("Validation interrupted by {}", checker.name());
                    break;
                }
            }
        } catch (Exception exception){
            logger.error("Validation failed", exception);
            errors.add(new ValidationError(SYSTEM, "Validation failed: " + exception.getMessage()));
        }

        return !errors.isEmpty()
                ? Result.fail(errors)
                : Result.success();
    }

    public Result<Nothing, List<ValidationError>> validate(@NonNull EntityT entity){ return validate(null, entity); }
}
