package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuildingRepository extends CrudRepository<Building, Long> {

    Building findByUsername(String userName);
}
