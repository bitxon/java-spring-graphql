package bitxon.spring.grapql.test;

import bitxon.spring.grapql.ext.TestcontainersConfig;
import bitxon.spring.grapql.model.Organization;
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
class OrganizationMutationTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void createOrganization() {
        // given
        var name = "New Company Name1";
        // language=GraphQL
        String query = """
            mutation CreateOrganization($name: String!) {
              createOrganization(name: $name) {
                id
                name
              }
            }
            """;

        // when
        var result = graphQlTester.document(query)
            .variable("name", name)
            .execute()
            .path("data.createOrganization")
            .entity(Organization.class)
            .get();

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
    }
}
