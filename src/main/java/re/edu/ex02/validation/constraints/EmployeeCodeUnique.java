package re.edu.ex02.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import re.edu.ex02.validation.impl.EmployeeCodeValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeCodeValidator.class)
public @interface EmployeeCodeUnique {
    String message() default "Mã nhân viên đã tồn tại";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
