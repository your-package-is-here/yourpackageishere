package com.teamshort.rocks.YourPackageIsHere.controller;

import com.teamshort.rocks.YourPackageIsHere.exception.AppException;
import com.teamshort.rocks.YourPackageIsHere.model.Role;
import com.teamshort.rocks.YourPackageIsHere.model.RoleName;
import com.teamshort.rocks.YourPackageIsHere.model.Tenant;
import com.teamshort.rocks.YourPackageIsHere.payload.ApiResponse;
import com.teamshort.rocks.YourPackageIsHere.payload.BuildingSummary;
import com.teamshort.rocks.YourPackageIsHere.payload.NewTenantRequest;
import com.teamshort.rocks.YourPackageIsHere.payload.SignUpRequest;
import com.teamshort.rocks.YourPackageIsHere.repository.BuildingRepository;
import com.teamshort.rocks.YourPackageIsHere.model.Building;
import com.teamshort.rocks.YourPackageIsHere.repository.TenantRepository;
import com.teamshort.rocks.YourPackageIsHere.security.BuildingPrincipal;
import com.teamshort.rocks.YourPackageIsHere.security.CurrentBuilding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TenantController {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private TenantRepository tenantRepository;


//    @Autowired
//    private PollService pollService;
    //       this.firstname = firstname;
    //        this.lastname = lastname;
    //        this.email = email;
    //        this.aptnum = aptnum;
    //        this.phonenum = phonenum;
    //        this.building = building;

    private static final Logger logger = LoggerFactory.getLogger(BuildingController.class);

    @PostMapping("/tenant/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addTenant(@CurrentBuilding BuildingPrincipal currentBuilding, @Valid @RequestBody NewTenantRequest newTenantRequest) {
        Optional<Building> building = buildingRepository.findByUsername(currentBuilding.getUsername());
        // Creating tenant's account
        Tenant tenant = new Tenant(newTenantRequest.getFirstname(), newTenantRequest.getLastname(), newTenantRequest.getEmail(), newTenantRequest.getAptnum(), newTenantRequest.getPhonenum(), building.get());

        Tenant result = tenantRepository.save(tenant);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/api/users/{tenant}")
//                .buildAndExpand(result.).toUri();

        return ResponseEntity.ok(new ApiResponse(true, "Tenant registered successfully"));
    }
//
    @GetMapping("/tenant/all")
    public Set<Tenant> getAllTenants(@CurrentBuilding BuildingPrincipal currentBuilding) {
        Optional<Building> building = buildingRepository.findByUsername(currentBuilding.getName());
        
        return building.get().getTenants();
    }
//
//    @GetMapping("/tenant/{id}")
//    public String getTenantPage(@PathVariable String id, Model m) {
//        long ID = Long.parseLong(id);
//        Tenant tenant = tenantRepository.findById(ID);
//        m.addAttribute("tenant", tenant);
//        return "tenant";
//    }
//
//    @GetMapping("/tenant/add")
//    public String getTenantAddPage() {
//        return "tenant";
//    }
//
//    @PostMapping("/tenantcreate")
//    public RedirectView createTenant(Principal p, String firstname, String lastname, String email, String aptnum, String phonenum) throws ParseException {
//        Optional<Building> building = buildingRepository.findByUsername(p.getName());
//        Tenant tenant = new Tenant(firstname,lastname,email,aptnum,phonenum, building.get());
//        tenantRepository.save(tenant);
//
//        return new RedirectView("/tenant/all");
//    }
//
//    @PutMapping("/tenantedit")
//    public RedirectView editTenant(Principal p, String id, String firstname, String lastname, String email, String aptnum, String phonenum) throws ParseException {
//        long ID = Long.parseLong(id);
//        Tenant tenant = tenantRepository.findById(ID);
//        tenant.setFirstname(firstname);
//        tenant.setLastname(lastname);
//        tenant.setEmail(email);
//        tenant.setAptnum(aptnum);
//        tenant.setPhonenum(phonenum);
//        tenantRepository.save(tenant);
//
//        return new RedirectView("/tenant/all");
//    }
//
//    @DeleteMapping("/tenant/{id}/delete")
//    public String deleteTenant(Principal p, @PathVariable String id, Model m) {
//        long ID = Long.parseLong(id);
//        Tenant tenant = tenantRepository.findById(ID);
//        tenantRepository.delete(tenant);
//        return getAllTenants(p, m);
//    }

}
