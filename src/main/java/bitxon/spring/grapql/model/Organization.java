package bitxon.spring.grapql.model;


import java.util.Set;

public record Organization(
    Integer id,
    String name,
    Set<Employee> employees) {

    public record Employee(
        Integer id,
        String email,
        String name
    ) {}
}
