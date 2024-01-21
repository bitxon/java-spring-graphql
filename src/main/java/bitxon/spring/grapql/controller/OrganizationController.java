package bitxon.spring.grapql.controller;

import bitxon.spring.grapql.db.OrganizationRepository;
import bitxon.spring.grapql.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationRepository organizationRepository;

    @QueryMapping
    @Transactional(readOnly = true)
    public Window<Organization> organizations(ScrollSubrange subrange) {
        var position = subrange.position().orElse(ScrollPosition.offset());
        var limit = Limit.of(subrange.count().orElse(10));
        var sort = Sort.by("id").ascending();
        return organizationRepository.findAllBy(position, limit, sort);
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public Organization organization(@Argument Integer id) {
        return organizationRepository.findById(id).orElse(null);
    }

    @MutationMapping
    @Transactional
    public Organization createOrganization(@Argument String name) {
        var organization = new Organization(null, name, Set.of());
        return organizationRepository.save(organization);
    }
}
