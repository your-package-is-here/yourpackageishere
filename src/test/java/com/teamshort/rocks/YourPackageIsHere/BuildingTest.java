package com.teamshort.rocks.YourPackageIsHere;

import org.junit.Test;
import static org.junit.Assert.*;

public class BuildingTest {

    @Test
    public void testBuildingDefaultConstructor() {
        Building building = new Building();
        assertNull(building.tenants);
        assertNull(building.city);
        assertNull(building.email);
        assertNull(building.password);
        assertNull(building.name);
        assertNull(building.streetaddress);

    }

    @Test
    public void testBuildingConstructor(){
//        Building(String username, String name, String streetaddress, String city, String state, String zip, String email, String password)
    Building building = new Building("bloop","Bloop Building","1 Bloop Ave",
            "Bloop City", "WA", "90210","bloop@bloop.com","bloop123");
    assertEquals("bloop",building.username);
    assertEquals("Bloop Building", building.name);
    assertEquals("1 Bloop Ave", building.streetaddress);
    assertEquals("Bloop City",building.city);
    assertEquals("WA", building.state);
    assertEquals("90210", building.zip);
    assertEquals("bloop@bloop.com", building.email);
    assertEquals("bloop123", building.password);

    }

}
