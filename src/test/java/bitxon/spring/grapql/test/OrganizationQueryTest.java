package bitxon.spring.grapql.test;

import bitxon.spring.grapql.ext.TestcontainersConfig;
import bitxon.spring.grapql.model.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.Set;

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
        var expected = new Organization(1, "The Best Software Inc", Set.of());
        // language=GraphQL
        String query = """
            query GetOrganizationById($id: ID!) {
              organization(id: $id) {
                id
                name
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
        assertThat(result)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    void getOrganizationsWithLimit() {
        // given
        var expected = List.of(
            new Organization(1, "The Best Software Inc", Set.of()),
            new Organization(2, "The Hardware Solutions", Set.of()),
            new Organization(3, "The John & Partners", Set.of())
        );
        // language=GraphQL
        String query = """
            query GetAllOrganizations($first: Int){
              organizations(first: $first) {
                pageInfo {
                  hasNextPage
                  endCursor
                }
                edges {
                  node {
                    id
                    name
                  }
                }
              }
            }
            """;

        // when
        var result = graphQlTester.document(query)
            .variable("first", 3)
            .execute()
            .path("data.organizations.edges[*].node")
            .entityList(Organization.class)
            .get();

        // then
        assertThat(result)
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyInAnyOrderElementsOf(expected);
    }
}
