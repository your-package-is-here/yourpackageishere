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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

//Resources: Jason Hiskey - https://codefellows.slack.com/files/UBJJGNL82/FKY9V3BNY/integration_testing_guide.java

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration
public class TenantControllerTest {

    //  -----------  INTEGRATION TESTS FOR GET REQS  ----------------  //

    @Autowired
    static
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    private InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix("*.html");

        return viewResolver;
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // This test will work on a page that DOES NOT require auth.
    @Test
    public void testingAPageThatDoesntRequireAuth() throws Exception {
        mockMvc.perform(get("/")).andExpect(content().string(containsString("Home")));
    }

    // This will create a auth token that will be injected into a test of a page that requires auth. Make sure that the user is a legitimate user that exists in your DB
    public static RequestPostProcessor testUser() throws Exception {
        return user("building1").password("buildingmanager1234").roles("ADMIN");
    }

    // This test will work on a page that DOES require auth.
//    @WithMockUser
//    @Test
//    public void testingAPageThatRequiresAuth() throws Exception{
//        mockMvc.perform(get("/SOMEROUTE").with(testUser())).andExpect(content().string(containsString("SOMESTRING")));
//    }


    //@GetMapping("/tenant/all")
//    public String getAllTenants(Principal principal, Model model)
    @WithMockUser
    @Test
    public void testgetAllTenants() throws Exception {
        mockMvc.perform(
                get("/tenant/all").with(testUser())).andExpect(content().string(containsString("Tenants")));
//                .andExpect(header().string("location", containsString("/tenant/all")));
    }

//@GetMapping("/tenant/{id}")
//    public String getTenantPage(@PathVariable String id, Model m)
//
//@GetMapping("/tenant/add")
//    public String getTenantAddPage()
//
//@PostMapping("/tenantcreate")
//    public RedirectView createTenant(Principal p, String firstname, String lastname, String email, String aptnum, String phonenum)
//
//@PostMapping("/tenantedit")
//    public RedirectView editTenant(Principal p, String id, String firstname, String lastname, String email, String aptnum, String phonenum)



}
