package bitxon.spring.grapql.model;

public record Employee(
    Integer id,
    String email,
    String name,
    Organization organization
) {

    public record Organization(
        String id,
        String name
    ) {}
}
