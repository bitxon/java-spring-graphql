package bitxon.spring.grapql.controller;

import bitxon.spring.grapql.db.EmployeeRepository;
import bitxon.spring.grapql.db.OrganizationRepository;
import bitxon.spring.grapql.model.Employee;
import bitxon.spring.grapql.model.EmployeeInput;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;


    @QueryMapping
    @Transactional(readOnly = true)
    public List<Employee> employees() {
        return employeeRepository.findAll();
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public Employee employee(@Argument Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @MutationMapping
    @Transactional
    public Employee createEmployee(@Argument EmployeeInput employee) {
        var organizationRef = organizationRepository.getReferenceById(employee.organizationId());
        var employeeJpa = new Employee(null, employee.email(), employee.name(), organizationRef);
        return employeeRepository.save(employeeJpa);
    }
}
