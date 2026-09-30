package re.edu.ex02.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re.edu.ex02.dto.request.EmployeeCreateDTO;
import re.edu.ex02.dto.request.EmployeeUpdateDTO;
import re.edu.ex02.dto.response.ApiResponse;
import re.edu.ex02.dto.response.EmployeeResponseDTO;
import re.edu.ex02.dto.response.PageResponse;
import re.edu.ex02.entity.Employee;
import re.edu.ex02.entity.enums.Department;
import re.edu.ex02.mapper.EmployeeMapper;
import re.edu.ex02.service.EmployeeService;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);

        ApiResponse<EmployeeResponseDTO> response = ApiResponse.<EmployeeResponseDTO>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message("Lấy nhân viên thành công!")
                .data(employeeMapper.toResponseDTO(employee))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PageResponse<EmployeeResponseDTO>>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Department department,
            @RequestParam(required = false) BigDecimal minSalary,
            @RequestParam(required = false) BigDecimal maxSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Employee> employeePage = employeeService.findAll(
                name, department, minSalary, maxSalary,
                page, size, sortBy, sortDir
        );

        Page<EmployeeResponseDTO> responsePage = employeePage.map(employeeMapper::toResponseDTO);

        PageResponse<EmployeeResponseDTO> data = PageResponse.<EmployeeResponseDTO>builder()
                        .content(responsePage.getContent())
                        .totalElements(responsePage.getTotalElements())
                        .totalPages(responsePage.getTotalPages())
                        .pageNumber(responsePage.getNumber())
                        .pageSize(responsePage.getSize())
                        .isFirst(responsePage.isFirst())
                        .isLast(responsePage.isLast())
                        .sortField(sortBy)
                        .sortDirection(sortDir)
                        .build();

        ApiResponse<PageResponse<EmployeeResponseDTO>> response = ApiResponse.<PageResponse<EmployeeResponseDTO>>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message("Lấy danh sách nhân viên thành công!")
                .data(data)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> save(
            @Valid @RequestBody EmployeeCreateDTO request
    ) {
        Employee employee = employeeService.save(
                employeeMapper.toEntity(request)
        );

        ApiResponse<EmployeeResponseDTO> response = ApiResponse.<EmployeeResponseDTO>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message("Thêm nhân viên thành công!")
                .data(employeeMapper.toResponseDTO(employee))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateDTO request
    ) {
        Employee employee = employeeService.update(id,employeeMapper.toEntity(request));

        ApiResponse<EmployeeResponseDTO> response = ApiResponse.<EmployeeResponseDTO>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message("Cập nhật nhân viên thành công!")
                .data(employeeMapper.toResponseDTO(employee))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        employeeService.delete(id);

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message("Xóa nhân viên thành công!")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
