package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TenantRepository extends CrudRepository<Tenant, Long> {
    List<Tenant> findByFirstnameIgnoreCaseAndLastnameIgnoreCase(String firstname, String lastname);
    List<Tenant> findByAptnumIgnoreCase(String aptnum);
    Tenant findById(long id);
    List<Tenant> findByBuilding(Building building);
}
