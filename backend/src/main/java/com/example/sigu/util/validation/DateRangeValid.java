package com.example.sigu.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface DateRangeValid {

    String message() default "La fecha de inicio no puede ser posterior o igual a la fecha de fin";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fechaInicio();
    String fechaFin();
}
