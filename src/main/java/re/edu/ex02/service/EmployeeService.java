package re.edu.ex02.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import re.edu.ex02.entity.Employee;
import re.edu.ex02.entity.enums.Department;
import re.edu.ex02.exception.DuplicateResourceException;
import re.edu.ex02.exception.InvalidRequestException;
import re.edu.ex02.exception.ResourceNotFoundException;
import re.edu.ex02.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id", "employeeCode", "fullName", "email", "phone", "department",
            "position", "salary", "hireDate", "status", "createdAt", "updatedAt"
    );

    private final EmployeeRepository employeeRepository;

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy nhân viên với id: " + id)
        );
    }

    public Page<Employee> findAll(
            String name,
            Department department,
            BigDecimal minSalary ,
            BigDecimal maxSalary ,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        validateSearchRequest(minSalary, maxSalary, page, size);

        String keyword = name == null ? "" : name.trim().toLowerCase();
        String sortField = normalizeSortField(sortBy);
        Sort.Direction sortDirection = parseSortDirection(sortDir);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        return employeeRepository.search(
                keyword, department, minSalary, maxSalary, pageable
        );
    }

    public Employee save(Employee request) {
        validateUnique(request);

        Employee employee = employeeRepository.save(request);

        log.info("Created employee: id = {}, employeeCode = {}", employee.getId(), employee.getEmployeeCode());

        return employee;
    }

    public Employee update(Long id, Employee request) {
        Employee employee = findById(id);

        validateUnique(id, request);

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());
        employee.setStatus(request.getStatus());

        Employee updatedEmployee = employeeRepository.save(employee);

        log.info("Updated employee: id = {}, employeeCode = {}", id, updatedEmployee.getEmployeeCode());

        return updatedEmployee;
    }

    public void delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);

        log.info("Deleted employee: id = {}, employeeCode = {}", id, employee.getEmployeeCode());
    }

    private void validateSearchRequest(
            BigDecimal minSalary,
            BigDecimal maxSalary,
            int page,
            int size
    ) {
        if (page < 0) {
            throw new InvalidRequestException("page phải lớn hơn hoặc bằng 0");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new InvalidRequestException("size phải nằm trong khoảng từ 1 đến " + MAX_PAGE_SIZE);
        }
        if (minSalary != null && minSalary.signum() < 0) {
            throw new InvalidRequestException("minSalary không được âm");
        }
        if (maxSalary != null && maxSalary.signum() < 0) {
            throw new InvalidRequestException("maxSalary không được âm");
        }
        if (minSalary != null && maxSalary != null && minSalary.compareTo(maxSalary) > 0) {
            throw new InvalidRequestException("minSalary không được lớn hơn maxSalary");
        }
    }

    private String normalizeSortField(String sortBy) {
        String sortField = sortBy == null ? "id" : sortBy.trim();
        if (!ALLOWED_SORT_FIELDS.contains(sortField)) {
            throw new InvalidRequestException("Trường sortBy không hợp lệ: " + sortField);
        }
        return sortField;
    }

    private Sort.Direction parseSortDirection(String sortDir) {
        String direction = sortDir == null ? "asc" : sortDir.trim();
        try {
            return Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException exception) {
            throw new InvalidRequestException("sortDir chỉ nhận asc hoặc desc");
        }
    }

    private void validateUnique(Employee request) {
        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException("Mã nhân viên đã tồn tại");
        }
        if (employeeRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        if (employeeRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Số điện thoại đã tồn tại");
        }
    }
    private void validateUnique(Long id, Employee request) {
        if (employeeRepository.existsByEmployeeCodeAndIdNot(request.getEmployeeCode(), id)) {
            throw new DuplicateResourceException("Mã nhân viên đã tồn tại");
        }
        if (employeeRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        if (employeeRepository.existsByPhoneAndIdNot(request.getPhone(), id)) {
            throw new DuplicateResourceException("Số điện thoại đã tồn tại");
        }
    }
}
