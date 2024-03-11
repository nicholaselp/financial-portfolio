package com.elpidoroun.financialportfolio.utilities;

import com.elpidoroun.financialportfolio.exceptions.ValidationException;

import static io.micrometer.common.util.StringUtils.isBlank;
import static java.util.Objects.isNull;

public class StringUtils {

    public static String requireNonBlank(String string, String errorMessage){
        if(isNull(string) || isBlank(string)){
            throw new ValidationException(errorMessage);
        }
        return string;
    }
}
