package com.example.sigu.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateRangeValidator implements ConstraintValidator<DateRangeValid, Object> {

    private String fechaInicioField;
    private String fechaFinField;

    @Override
    public void initialize(DateRangeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        this.fechaInicioField = constraintAnnotation.fechaInicio();
        this.fechaFinField = constraintAnnotation.fechaFin();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        try {
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
            Object fechaInicio = wrapper.getPropertyValue(this.fechaInicioField);
            Object fechaFin = wrapper.getPropertyValue(this.fechaFinField);

            if (fechaInicio == null || fechaFin == null) return true;

            boolean isValid = compararFechas(fechaInicio, fechaFin);

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(this.fechaInicioField)
                        .addConstraintViolation();
            }

            return isValid;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean compararFechas(Object fechaInicio, Object fechaFin) {
        if (fechaInicio instanceof LocalDate && fechaFin instanceof LocalDate) {
            return !((LocalDate) fechaInicio).isAfter((LocalDate) fechaFin);
        }

        if (fechaInicio instanceof LocalDateTime && fechaFin instanceof LocalDateTime) {
            return !((LocalDateTime) fechaInicio).isAfter((LocalDateTime) fechaFin);
        }

        if (fechaInicio instanceof Date && fechaFin instanceof Date) {
            return !((Date) fechaInicio).after((Date) fechaFin);
        }

        return true;
    }
}
