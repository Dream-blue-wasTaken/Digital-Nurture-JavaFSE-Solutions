package com.employeeapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
@SpringBootApplication
public class SpringRESTApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringRESTApplication.class, args);
    }
}
class EmployeeDTO {
    private Long id;
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    private String department;
    public EmployeeDTO() {}
    public EmployeeDTO(Long id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
@RestController
@RequestMapping("/api/employees")
class EmployeeRestController {
    private final List<EmployeeDTO> employees = new ArrayList<>();
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employees;
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        dto.setId((long) (employees.size() + 1));
        employees.add(dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        for (EmployeeDTO emp : employees) {
            if (emp.getId().equals(id)) {
                emp.setName(dto.getName());
                emp.setDepartment(dto.getDepartment());
                return ResponseEntity.ok(emp);
            }
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employees.removeIf(e -> e.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}