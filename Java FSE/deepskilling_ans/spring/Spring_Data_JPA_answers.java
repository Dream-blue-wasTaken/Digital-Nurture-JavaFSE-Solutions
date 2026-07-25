package com.employeemanagement;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;
@Entity
@Table(name = "departments")
class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees;
    public Department() {}
    public Department(String name) { this.name = name; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Employee> getEmployees() { return employees; }
}
@Entity
@Table(name = "employees")
class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Double salary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    public Employee() {}
    public Employee(String name, String email, Double salary, Department department) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Double getSalary() { return salary; }
    public Department getDepartment() { return department; }
}
@Repository
interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContaining(String name);
    List<Employee> findByDepartmentName(String deptName);
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findHighEarners(@Param("salary") Double salary);
    @Query(value = "SELECT * FROM employees WHERE email LIKE %:domain", nativeQuery = true)
    List<Employee> findByEmailDomain(@Param("domain") String domain);
}
@Service
class EmployeeService {
    private final EmployeeRepository repository;
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }
    public List<Employee> getEmployeesByName(String name) {
        return repository.findByNameContaining(name);
    }
    public List<Employee> getHighEarners(Double minSalary) {
        return repository.findHighEarners(minSalary);
    }
}