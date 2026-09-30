package re.edu.ex02.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import re.edu.ex02.repository.EmployeeRepository;
import re.edu.ex02.validation.constraints.EmailUnique;

@Component
@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailUnique, String> {
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !employeeRepository.existsByEmailIgnoreCase(value);
    }
}
