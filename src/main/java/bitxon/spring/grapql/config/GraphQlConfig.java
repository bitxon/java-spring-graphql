package bitxon.spring.grapql.config;

import graphql.scalars.ExtendedScalars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

    private static final Logger log = LoggerFactory.getLogger(GraphQlConfig.class);

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return builder -> builder
            .scalar(ExtendedScalars.Date);
    }


    @Bean
    public GraphQlSourceBuilderCustomizer graphQlSourceBuilderCustomizer() {
        return source -> source.inspectSchemaMappings(report -> log.warn(report.toString()));
    }
}
