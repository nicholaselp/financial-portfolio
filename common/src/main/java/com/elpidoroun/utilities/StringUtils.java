package com.elpidoroun.utilities;


import com.elpidoroun.exception.ValidationException;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class StringUtils {

    public static String requireNonBlank(String string, String errorMessage){
        if(isNull(string) || isBlank(string)){
            throw new ValidationException(errorMessage);
        }
        return string;
    }

    public static String nullIfBlank(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        return str;
    }
}
