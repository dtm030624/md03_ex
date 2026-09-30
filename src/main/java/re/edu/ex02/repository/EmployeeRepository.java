package re.edu.ex02.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.edu.ex02.entity.Employee;
import re.edu.ex02.entity.enums.Department;

import java.math.BigDecimal;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeCode(String employeeCode);
    boolean existsByPhone(String phone);
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmployeeCodeAndIdNot(String employeeCode, Long id);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsByPhoneAndIdNot(String phone, Long id);

    @Query("""
        SELECT e FROM Employee e
        WHERE LOWER(e.fullName) LIKE CONCAT('%', :name, '%')
                AND (:department IS NULL OR e.department = :department)
                AND (:minSalary IS NULL OR e.salary >= :minSalary )
                AND (:maxSalary IS NULL OR e.salary <= :maxSalary)
        """)
    Page<Employee> search(
            @Param("name") String name,
            @Param("department") Department department,
            @Param("minSalary") BigDecimal minSalary,
            @Param("maxSalary") BigDecimal maxSalary,
            Pageable pageable
    );
}
