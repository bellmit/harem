package com.yimayhd.palace.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationException;
import java.util.Set;

//import javax.validation.ValidationException;

/**
 * Created by xushubing on 2016/8/31.
 */
public class ValidationUtil {
    private static Validator validator;

    static {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }


    /**
     * @param t 将要校验的对象
     * @throws ValidationException
     * @throws ValidationException
     * @throws ValidationException void
     * @throws
     * @Description: 校验方法
     */
    public static <T> void validate(T t) throws ValidationException {
        Set<ConstraintViolation<T>> set = validator.validate(t);
        if (set.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> val : set) {
                validateError.append(val.getMessage() + " ;");
            }
            throw new ValidationException(validateError.toString());
        }
    }

}
