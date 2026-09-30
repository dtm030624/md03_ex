package re.edu.ex02.mapper;

import org.springframework.stereotype.Component;
import re.edu.ex02.dto.request.EmployeeCreateDTO;
import re.edu.ex02.dto.request.EmployeeUpdateDTO;
import re.edu.ex02.dto.response.EmployeeResponseDTO;
import re.edu.ex02.entity.Employee;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeCreateDTO dto) {
        return Employee.builder()
                .employeeCode(dto.getEmployeeCode())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .salary(dto.getSalary())
                .hireDate(dto.getHireDate())
                .status(dto.getStatus())
                .build();
    }

    public Employee toEntity(EmployeeUpdateDTO dto) {
        return Employee.builder()
                .employeeCode(dto.getEmployeeCode())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .position(dto.getPosition())
                .salary(dto.getSalary())
                .hireDate(dto.getHireDate())
                .status(dto.getStatus())
                .build();
    }

    public EmployeeResponseDTO toResponseDTO(Employee employee) {
        return EmployeeResponseDTO.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .department(employee.getDepartment())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .hireDate(employee.getHireDate())
                .status(employee.getStatus())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}