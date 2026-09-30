package re.edu.ex02.dto.response;

import lombok.*;
import re.edu.ex02.entity.enums.Department;
import re.edu.ex02.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeResponseDTO {
    private Long id;
    private String employeeCode;
    private String fullName;
    private String email;
    private String phone;
    private Department department;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
