package re.edu.ex02.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import re.edu.ex02.entity.enums.Department;
import re.edu.ex02.entity.enums.Status;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeCreateDTO {
    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^EMP-\\d{4}$", message = "Định dạng EMP-XXXX")
    private String employeeCode;

    @NotBlank(message = "Không được bỏ trống")
    @Size(min = 3, max = 50, message = "Họ và tên nhân viên từ 3 - 50 ký tự")
    private String fullName;

    @NotBlank(message = "Không được bỏ trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Không được bỏ trống")
    @Pattern(regexp = "^0\\d{9}$")
    private String phone;

    @NotNull(message = "Không được để null")
    private Department department;

    private String position;

    @NotNull(message = "Không được để null")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal salary;

    @NotNull(message = "Không được để null")
    @PastOrPresent
    private LocalDate hireDate;

    @NotNull(message = "Không được để null")
    private Status status;
}
