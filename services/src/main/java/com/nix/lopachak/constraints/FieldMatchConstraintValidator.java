package com.nix.lopachak.constraints;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Class содержит методы реализации для сопоставления полей пароля на соответствие
 *
 * @author Volodymyr Lopachak
 * @version 1.0
 */
public class FieldMatchConstraintValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;

    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            Object firstValue = BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName).getReadMethod().invoke(value);
            Object secondValue = BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName).getReadMethod().invoke(value);
            return (firstValue == null && secondValue == null) || (firstValue != null && firstValue.equals(secondValue));
        } catch (Exception ignore) {
            return false;
        }
    }

    public static void addFieldErrorForFieldMatchConstraint(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = new ArrayList<>();
        for (final ObjectError objectError : bindingResult.getAllErrors()) {
            for (final String code : objectError.getCodes()) {
                if (FieldMatch.class.getSimpleName().equals(code)) {
                    final ConstraintViolation violation = objectError.unwrap(ConstraintViolation.class);
                    final Object value = violation.getInvalidValue();
                    final FieldMatch fieldMatch = AnnotationUtils.findAnnotation(value.getClass(), FieldMatch.class);
                    if (fieldMatch != null) {
                        final String fieldName = fieldMatch.second();
                        final Field field = ReflectionUtils.findField(value.getClass(), fieldName);
                        if (field == null) {
                            throw new IllegalStateException(format("'%s' class does not contain the '%s' field!", value.getClass(), fieldName));
                        }
                        field.setAccessible(true);
                        final Object fieldValue = ReflectionUtils.getField(field, value);
                        fieldErrors.add(
                                new FieldError(objectError.getObjectName(), fieldName, fieldValue, false,
                                        objectError.getCodes(), objectError.getArguments(), objectError.getDefaultMessage()
                                )
                        );
                        break;
                    }
                }
            }
        }
        for (final FieldError fieldError : fieldErrors) {
            bindingResult.addError(fieldError);
        }
    }
}

