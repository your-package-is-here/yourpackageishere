package com.teamshort.rocks.YourPackageIsHere;

import org.junit.Test;

import static org.junit.Assert.*;

public class TenantTest {

    @Test
    public void testTenantDefaultConstructor() {
        Tenant tenant = new Tenant();
        assertNull(tenant.firstname);
        assertNull(tenant.lastname);
        assertNull(tenant.aptnum);
        assertNull(tenant.email);
        assertNull(tenant.phonenum);
        assertNull(tenant.building);

    }

    @Test
    public void testTenantConstructor(){
//  Tenant(String firstname, String lastname, String email, String aptnum, String phonenum, Building building)
        Tenant tenant = new Tenant("Bloopy","Blooperson","bloop@bloop.com","1D","111-111-1111", new Building());
        assertEquals("Bloopy",tenant.firstname);
        assertEquals("Blooperson", tenant.lastname);
        assertEquals("bloop@bloop.com", tenant.email);
        assertEquals("1D", tenant.aptnum);
        assertEquals("111-111-1111", tenant.phonenum);

    }
}
