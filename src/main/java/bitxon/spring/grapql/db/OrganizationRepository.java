package bitxon.spring.grapql.db;

import bitxon.spring.grapql.model.Organization;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    Window<Organization> findAllBy(ScrollPosition position, Limit limit, Sort sort);
}
