package bitxon.spring.grapql.mapper;

import bitxon.spring.grapql.db.model.EmployeeEntity;
import bitxon.spring.grapql.db.model.OrganizationEntity;
import bitxon.spring.grapql.model.Employee;
import bitxon.spring.grapql.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface EntityMapper {

    Employee mapToApi(EmployeeEntity jpa);

    Organization mapToApi(OrganizationEntity jpa);
}
