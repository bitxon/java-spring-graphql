package bitxon.spring.grapql.model;


public record EmployeeInput(
    String email,
    String name,
    Integer organizationId
) {}