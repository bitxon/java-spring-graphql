package bitxon.spring.grapql.db;

import bitxon.spring.grapql.db.model.EmployeeEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeRepository extends ListCrudRepository<EmployeeEntity, Integer> {
}
