package de.spiegel.timeregistration.business.validation;

import de.spiegel.timeregistration.business.ValidEntity;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ingoveith
 */
public class CrossCcheckConstraintValidator implements ConstraintValidator<CrossCheck, ValidEntity> {

    @Override
    public void initialize(CrossCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(ValidEntity entity, ConstraintValidatorContext context) {
        return entity.isValide();
    }
}
