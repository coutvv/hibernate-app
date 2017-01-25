package ru.coutvv.lifecycle.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by coutvv on 25.01.2017.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {QuadrantIIIValidator.class})
@Documented
public @interface NoQuadrantIII {
    String message() default "failed quadrant III test";

    Class<?> [] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
