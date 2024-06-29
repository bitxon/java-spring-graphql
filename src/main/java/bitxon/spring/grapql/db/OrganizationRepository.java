package bitxon.spring.grapql.db;

import bitxon.spring.grapql.db.model.OrganizationEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer> {

    @Override
    @EntityGraph(attributePaths = {"employees"}) // Fix N+1 problem : Option #1
    Optional<OrganizationEntity> findById(Integer id);

    @EntityGraph(attributePaths = {"employees"}) // Fix N+1 problem : Option #1
    Window<OrganizationEntity> findAllBy(ScrollPosition position, Limit limit, Sort sort);
}
