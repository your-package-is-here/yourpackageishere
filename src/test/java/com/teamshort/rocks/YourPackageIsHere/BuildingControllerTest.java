package com.teamshort.rocks.YourPackageIsHere;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BuildingControllerTest {

    //  -----------  INTEGRATION TESTS FOR GET REQS  ----------------  //

//    @GetMapping("/")
//    public String getHomePage(Principal principal, Model model)
//
//@GetMapping("/login")
//    public String getLoginPage(Principal principal, Model model)
//
//@GetMapping("/register")
//    public String getRegisterPage(Principal principal, Model model)
//
//@GetMapping("/aboutus")
//    public String getSignUpPage(Principal principal, Model model)
//
//@PostMapping("/buildingcreate")
//    public RedirectView createUser(String username, String name, String streetaddress, String city, String state, String zip, String email, String password) throws ParseException
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

    //Private helper test function to initialize a Building instance
    private Building initialize(){
        return new Building("bloop","Bloop Building","1 Bloop Ave",
                "Bloop City", "WA", "90210","bloop@bloop.com","bloop123");

    }

    @Test
    public void testBuildingCRUD() throws Exception {
        Building building = initialize();

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

        assertEquals(building.name,buildingResCreate.getName()); // check Creation and Read data

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
