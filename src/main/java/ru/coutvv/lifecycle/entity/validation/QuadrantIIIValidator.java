package ru.coutvv.lifecycle.entity.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by coutvv on 25.01.2017.
 */
public class QuadrantIIIValidator implements ConstraintValidator<NoQuadrantIII, Coordinate> {


    @Override
    public void initialize(NoQuadrantIII constraintAnnotation) {

    }

    @Override
    public boolean isValid(Coordinate value, ConstraintValidatorContext context) {
        return !(value.getX() < 0 && value.getY() < 0);
    }
}
