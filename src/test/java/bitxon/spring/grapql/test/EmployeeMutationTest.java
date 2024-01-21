package bitxon.spring.grapql.test;

import bitxon.spring.grapql.ext.TestcontainersConfig;
import bitxon.spring.grapql.model.Employee;
import bitxon.spring.grapql.model.EmployeeInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@AutoConfigureGraphQlTester
@Import(TestcontainersConfig.class)
class EmployeeMutationTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void createEmployee() {
        // given
        var employeeInput = new EmployeeInput("John Doe", "john.doe@email.com", 3);
        // language=GraphQL
        String query = """
            mutation CreateEmployee($name: String!, $email: String!, $organizationId: Int!) {
              createEmployee(employee: { name: $name, email: $email, organizationId: $organizationId}) {
                id
                name
                email
              }
            }
            """;

        // when
        var result = graphQlTester.document(query)
            .variable("name", employeeInput.name())
            .variable("email", employeeInput.email())
            .variable("organizationId", employeeInput.organizationId())
            .execute()
            .path("data.createEmployee")
            .entity(Employee.class)
            .get();

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(employeeInput.name());
        assertThat(result.getEmail()).isEqualTo(employeeInput.email());
    }
}
