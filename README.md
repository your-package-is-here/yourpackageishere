# Your Package Is Here
401 Mid-Course Java Project

### Summary
This Java application is designed to be used by a building manager to scan received 
packages in the building for tenants' delieveries and send an email as a notification.


### Developers
   - [Kush Shrestha](https://github.com/kushshrestha01)
   - [Reina Vencer](https://github.com/river-ceanne)
   - [Devon Hackley](https://github.com/devonhackley)
   - [John Winters](https://github.com/thatsjustjohn)
   

### Frameworks & Technologies Used
   - Spring Boot (MVC)
   - Bootstrap
   
### Starter Guide


#### Deployed Link: [TO DO . . . ]()

### Entities
- [Building](./src/main/java/com/teamshort/rocks/YourPackageIsHere/Building.java)
- [Tenant](./src/main/java/com/teamshort/rocks/YourPackageIsHere/Tenant.java)

### Controllers
- [BuildingController](./src/main/java/com/teamshort/rocks/YourPackageIsHere/BuildingController.java)
- [TenantController](./src/main/java/com/teamshort/rocks/YourPackageIsHere/TenantController.java)

### CRUD Repositories
- [BuildingRepository](./src/main/java/com/teamshort/rocks/YourPackageIsHere/BuildingRepository.java)
- [TenantRepository](./src/main/java/com/teamshort/rocks/YourPackageIsHere/TenantRepository.java)

### Routes
    @GetMapping("/")
        public String getHomePage(Principal principal, Model model)
    
    @GetMapping("/login")
        public String getLoginPage(Principal principal, Model model)
        
    @GetMapping("/register")
        public String getRegisterPage(Principal principal, Model model)
    
    @GetMapping("/aboutus")
        public String getSignUpPage(Principal principal, Model model)
        
    @PostMapping("/buildingcreate")
        public RedirectView createUser(String username, String name, String streetaddress, String city, String state, String zip, String email, String password) throws ParseException
    
    @GetMapping("/tenant/all")
        public String getAllTenants(Principal principal, Model model)
    
    @GetMapping("/tenant/{id}")
        public String getTenantPage(@PathVariable String id, Model m)
    
    @GetMapping("/tenant/add")
        public String getTenantAddPage()
        
    @PostMapping("/tenantcreate")
        public RedirectView createTenant(Principal p, String firstname, String lastname, String email, String aptnum, String phonenum)    
    
    @PostMapping("/tenantedit")
        public RedirectView editTenant(Principal p, String id, String firstname, String lastname, String email, String aptnum, String phonenum)     

### API