package bitxon.spring.grapql.controller;

import bitxon.spring.grapql.db.EmployeeRepository;
import bitxon.spring.grapql.db.OrganizationRepository;
import bitxon.spring.grapql.db.model.EmployeeEntity;
import bitxon.spring.grapql.mapper.EntityMapper;
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
    private final EntityMapper entityMapper;


    @QueryMapping
    @Transactional(readOnly = true)
    public List<Employee> employees() {
        return employeeRepository.findAll().stream()
            .map(entityMapper::mapToApi)
            .toList();
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public Employee employee(@Argument Integer id) {
        return employeeRepository.findById(id)
            .map(entityMapper::mapToApi)
            .orElse(null);
    }

    @MutationMapping
    @Transactional
    public Employee createEmployee(@Argument EmployeeInput employee) {
        var organizationRef = organizationRepository.getReferenceById(employee.organizationId());
        var employeeEntity = new EmployeeEntity(null, employee.email(), employee.name(), organizationRef);
        return entityMapper.mapToApi(employeeRepository.save(employeeEntity));
    }
}
