package com.teamshort.rocks.YourPackageIsHere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TenantController {

    @Autowired
    TenantRepository appUserRepository;

    @GetMapping("/tenant/{type}")
    public String getTenantPage() {
        return "tenant";
    }

}
