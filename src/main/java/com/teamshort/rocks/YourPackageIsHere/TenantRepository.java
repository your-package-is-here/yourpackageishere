package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.data.repository.CrudRepository;

public interface TenantRepository extends CrudRepository<Tenant, Long> {

    Tenant findById(long id);
    Tenant findByEmail(String email);

}
