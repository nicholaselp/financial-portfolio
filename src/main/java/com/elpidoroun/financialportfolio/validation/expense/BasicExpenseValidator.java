package com.elpidoroun.financialportfolio.validation.expense;

import com.elpidoroun.financialportfolio.utilities.Nothing;
import com.elpidoroun.financialportfolio.utilities.Result;
import com.elpidoroun.financialportfolio.validation.EntityValidator;
import com.elpidoroun.financialportfolio.validation.ValidationError;

import javax.validation.ValidationException;

public class BasicExpenseValidator implements EntityValidator {
    @Override
    public Result<Nothing, ValidationError> validate(Object original, Object entity) throws ValidationException {
        return null;
    }

    @Override
    public int priority() {
        return 0;
    }

    @Override
    public String name() {
        return "BasicExpenseValidator";
    }
}
