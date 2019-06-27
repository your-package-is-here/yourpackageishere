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
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ContextConfiguration
public class BuildingControllerTest {

    //  -----------  INTEGRATION TESTS FOR GET REQS  ----------------  //
//@GetMapping("/aboutus")
//    public String getSignUpPage(Principal principal, Model model)
//

    //  -----------  END-TO-END TESTS  ----------------  //
// Resources:
// https://stackoverflow.com/questions/38966718/mockmvc-status-expected200-but-was302
// https://stackoverflow.com/questions/17972428/mock-mvc-add-request-parameter-to-test
// https://memorynotfound.com/unit-test-spring-mvc-rest-service-junit-mockito/#unit-test-http-post

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BuildingRepository buildingRepository;

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


    //Private helper test function to initialize a Building instance
    private Building initialize(){
        return new Building("bloop","Bloop Building","1 Bloop Ave",
                "Bloop City", "WA", "90210","bloop@bloop.com","bloop123");

    }

    public static RequestPostProcessor testUser() throws Exception {
        return user("bloop").password("bloop123").roles("ADMIN");
    }

    // This test will work on a page that DOES NOT require auth.
    @Test
    public void testingAPageThatDoesntRequireAuth() throws Exception {
        mockMvc.perform(get("/")).andExpect(content().string(containsString("Home")));
    }

    // This route requires auth.
    @WithMockUser
    @Test
    public void testGetSendEmail() throws Exception {
        Building building = initialize();
        mockMvc.perform(
                get("/sendemail").with(testUser())).andExpect(content().string(containsString("Scan Package")));
    }

    @Test
    public void testSendEmailHelper() throws Exception {
        mockMvc.perform(
                post("")
        )

    }

    @Test
    public void testBuildingCRUD() throws Exception {
        mockMvc.perform(
                post("/buildingcreate")
                        .param("username", "bloop")
                        .param("name", "Bloop Building")
                        .param("streetaddress", "1 Bloop Ave")
                        .param("city", "Bloop City")
                        .param("state", "WA")
                        .param("zip",  "90210")
                        .param("email", "bloop@bloop.com")
                        .param("password", "bloop123"))
                .andDo(print())
                .andExpect(header().string("location", containsString("/")));

        Building buildingResCreate = buildingRepository.findByUsername("bloop");

        assertEquals("Bloop Building",buildingResCreate.getName()); // check Creation and Read data

        //UPDATE instance' name
        buildingResCreate.setName("New Name");
        buildingRepository.save(buildingResCreate);

        Building buildingResUpdate = buildingRepository.findByUsername("bloop");


        assertEquals("New Name",buildingResUpdate.getName()); // check Update and Read data


        //DELETE created test building instance from DB table
        buildingRepository.delete(buildingResUpdate);
        assertNull(buildingRepository.findByUsername("bloop"));

    }

}
