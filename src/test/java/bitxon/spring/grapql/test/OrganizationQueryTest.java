package bitxon.spring.grapql.test;

import bitxon.spring.grapql.ext.TestcontainersConfig;
import bitxon.spring.grapql.model.Organization;
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
class OrganizationQueryTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void getOrganizationById() {
        // given
        // language=GraphQL
        String query = """
            query GetOrganizationById($id: ID!) {
              organization(id: $id) {
                id
                name
                employees {
                    name
                }
              }
            }
            """;

        // when
        var result = graphQlTester.document(query)
            .variable("id", 1)
            .execute()
            .path("data.organization")
            .entity(Organization.class)
            .get();

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.name()).isEqualTo("The Best Software Inc");
        assertThat(result.employees()).allSatisfy(employee -> {
            assertThat(employee.id()).isNull();
            assertThat(employee.email()).isNull();
            assertThat(employee.name()).isNotBlank();
        });
    }

    @Test
    void getOrganizationsWithLimit() { // Reproduce N+1 problem - See logs
        // given
        // language=GraphQL
        String query = """
            query GetAllOrganizations($first: Int, $after: String, $last: Int, $before: String) {
              organizations(first: $first, after: $after, last: $last, before: $before) {
                pageInfo {
                  hasNextPage
                  endCursor
                }
                edges {
                  node {
                    id
                    name
                    employees {
                      name
                    }
                  }
                }
              }
            }
            """;

        // when
        var result = graphQlTester.document(query)
            .variable("first", 5)
            .execute()
            .path("data.organizations.edges[*].node")
            .entityList(Organization.class)
            .get();

        // then
        assertThat(result).hasSize(5).allSatisfy(organization -> {
            assertThat(organization).hasNoNullFieldsOrProperties();
            assertThat(organization.employees()).allSatisfy(employee -> {
                assertThat(employee.id()).isNull();
                assertThat(employee.email()).isNull();
                assertThat(employee.name()).isNotBlank();

            });
        });
    }
}
