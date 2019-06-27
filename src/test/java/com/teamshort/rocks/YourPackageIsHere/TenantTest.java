package com.teamshort.rocks.YourPackageIsHere;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.MockServerConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;


import org.springframework.context.annotation.Configuration;


import javax.naming.Context;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


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

    //Private helper test function to initialize a Tenant instance
    private Tenant initialize(){
        return new Tenant("Bloopy","Blooperson","bloop@bloop.com","1D","111-111-1111", new Building());
    }

    @Test
    public void testTenantConstructor(){
//  Tenant(String firstname, String lastname, String email, String aptnum, String phonenum, Building building)
        Tenant tenant = initialize();
        assertEquals("Bloopy",tenant.firstname);
        assertEquals("Blooperson", tenant.lastname);
        assertEquals("bloop@bloop.com", tenant.email);
        assertEquals("1D", tenant.aptnum);
        assertEquals("111-111-1111", tenant.phonenum);

    }

//  -----------  GETTER TESTS  ----------------  //
    @Test
    public void testGetFirstName(){
        Tenant tenant = initialize();
        assertEquals("Bloopy", tenant.getFirstname());

    }

    @Test
    public void testGetLastName(){
        Tenant tenant = initialize();
        assertEquals("Blooperson", tenant.getLastname());
    }

    @Test
    public void testGetEmail(){
        Tenant tenant = initialize();
        assertEquals("bloop@bloop.com", tenant.getEmail());
    }

    @Test
    public void testGetAptNum(){
        Tenant tenant = initialize();
        assertEquals("1D", tenant.getAptnum());
    }

    @Test
    public void testGetPhoneNum(){
        Tenant tenant = initialize();
        assertEquals("111-111-1111", tenant.getPhonenum());
    }

    //  -----------  SETTER TESTS  ----------------  //

    @Test
    public void testSetFirstName(){
        Tenant tenant = initialize();
        tenant.setFirstname("Blossom");
        assertEquals("Blossom", tenant.getFirstname());

    }

    @Test
    public void testSetLastName(){
        Tenant tenant = initialize();
        tenant.setLastname("Bubbles");
        assertEquals("Bubbles", tenant.getLastname());
    }

    @Test
    public void testSetEmail(){
        Tenant tenant = initialize();
        tenant.setEmail("bloopy@bloopy.com");
        assertEquals("bloopy@bloopy.com", tenant.getEmail());
    }

    @Test
    public void testSetAptNum(){
        Tenant tenant = initialize();
        tenant.setAptnum("2B");
        assertEquals("2B", tenant.getAptnum());
    }

    @Test
    public void testSetPhoneNum(){
        Tenant tenant = initialize();
        tenant.setPhonenum("222-222-2222");
        assertEquals("222-222-2222", tenant.getPhonenum());
    }


}
