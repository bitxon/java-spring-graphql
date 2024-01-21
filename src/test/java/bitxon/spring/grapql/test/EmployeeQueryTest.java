package bitxon.spring.grapql.test;

import bitxon.spring.grapql.ext.TestcontainersConfig;
import bitxon.spring.grapql.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureGraphQlTester
@Import(TestcontainersConfig.class)
class EmployeeQueryTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void getEmployeeById() {
        // given
        var expectedEmployee = new Employee(1, "alice@email.com", "Alice", null);
        // language=GraphQL
        String query = """
            query GetEmployeeById($id: ID!) {
              employee(id: $id) {
                id
                name
                email
              }
            }
            """;

        // when
        var result = graphQlTester.document(query)
            .variable("id", 1)
            .execute()
            .path("data.employee")
            .entity(Employee.class)
            .get();

        // then
        assertThat(result)
            .usingRecursiveComparison()
            .isEqualTo(expectedEmployee);
    }

    @Test
    void getEmployees() {
        // given
        // language=GraphQL
        String query = """
            query GetAllEmployees{
              employees {
                id
                name
                email
              }
            }
            """;

        // when
        var result = graphQlTester.document(query)
            .variable("id", 1)
            .execute()
            .path("data.employees")
            .entityList(Employee.class)
            .get();

        // then
        assertThat(result).isNotEmpty()
            .allSatisfy(employee -> {
                assertThat(employee.getId()).isNotNull();
                assertThat(employee.getName()).isNotBlank();
                assertThat(employee.getEmail()).isNotBlank();
            });
    }
}
