package com.elpidoroun.financialportfolio.service;

import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import com.elpidoroun.financialportfolio.validation.EntityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public Result<Nothing, List<String>> validate(@Nullable EntityT original, @NonNull EntityT entity){
        requireNonNull(entity, "Nothing to validate");

        List<String> errors = new ArrayList<>();

        try {
            for (EntityValidator<EntityT> checker : validators){
                var result = checker.validate(original, entity);
                if(result.isFail()){
                    errors.add(result.getError().orElse("Unexpected exception during validation"));
                }
                if(checker.interruptValidationChain(original, entity)){
                    logger.info("Validation interrupted by {}", checker.name());
                    break;
                }
            }
        } catch (Exception exception){
            logger.error("Validation failed", exception);
            errors.add("Validation failed with an exception ");
        }

        return !errors.isEmpty()
                ? Result.fail(errors)
                : Result.success();
    }

    public Result<Nothing, List<String>> validate(@NonNull EntityT entity){ return validate(null, entity); }
}
