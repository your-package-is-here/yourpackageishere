# Project Name: Your Package Is Here
401 Final Java Project (Back end)

### Summary
This Java application is designed to be used by a building manager to scan received 
packages in the building for tenants' delieveries and send an email and text as a notification.


### Developers
   - [Kush Shrestha](https://github.com/kushshrestha01)
   - [Levi Porter](https://github.com/levibrooke)
   - [Chaitanya Narukulla](https://github.com/chaitanyanarukulla)
   - [John Winters](https://github.com/thatsjustjohn)
   
   
### Problem Domain
When you receive an email from usps that you package has been delivered, Tenants are unsure if the package has been delivered to the apartment office or not. 
So using our app, building managers can scan the qr code in the package which will automatic send the email and text to the tenant that their package is in the office for pickup. Tenants are assured that
their package has been delivered and is safe stored in office.  


### User Stories
- MVP User Stories As a Tenant, I want to receive emails about packages that have arrived so I know it has been received.
- As a Building manager, I want to be able to sign up and create an account so that I can make full use of this service.
- As a Building manager, I want to be able to log in and add tenants, so that I can send them emails when packages are received.
- As a Building manager, I want to be able to log in and edit tenants, if they happen to change apartments or emails.
- As a Building manager, I want to be able to log in and delete tenants, if they move or are no longer residing.
- As a Building manager, I want to be able to log in and edit the manager information, if I change building names or emails.
- As a Building manager, I want to be able to log in and scan packages, So an auto generated email will notify my tenants of packages.


### Stretch User Stories
As a Building manager, I want to scan shipping labels and send automatic email to tenants that their package has arrived.
As a developers, leveraging delivery APIs to scan QR code or bar code to get the tenants information. 


### Frameworks & Technologies Used
   - Java
   	- Spring Boot
   - AWS Services 
   	- S3 (React front end)
	- EB (with Load Balancer)
	- RDS (PSQL)
	- Code Pipeline / Code Build
	- Amplify
	- Route 53 / ACM
   
### Starter Guide

- The Building manager will first visit the website and create an account for their building. 
- They will then add all the tenants information in their account.
- After a package arrives, the building manager can go to 'scan package' tab in 'building' which will open a webcam. If using mobile devices it will 
open the mobile camera.
- Scan the package which will auto populate the tenant's information in a form. 
- Click submit to send the email.
- After the submit button is clicked there will be a notification which will inform the user if the email has been sent. If there is no tenant by that name it will send an email to all of the tenants in that apartment. 
- If there are no matches it will give a message saying that there were no tenants found.

#### Deployed Link: 
- [api.yourpackageishere.com](api.yourpackageishere.com/)

### To run your backend locally:

1. Clone down the repository on your local machine with command:

           git clone https://github.com/your-package-is-here/yourpackageishere-backend.git

2. Make sure you have Postgres installed in your local machine. You must first create a database named 'yourpackageishere' in psql with command:
            
           CREATE DATABASE yourpackageishere;
           
4. On your application.properties, make sure postgres is set up on with your individual user environment variables.

        spring.datasource.url=${DATABASE_URL}
        spring.datasource.username=${DATABASE_USERNAME}
        spring.datasource.password=${DATABASE_PASSWORD}
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
        server.port=5000

5. Run the application through your IDE or use the terminal command:
        
        ./gradlew bootrun

6. You will now be able to run the back end on your local machine with URL:

        http://localhost:5000
        
        
### To run your backend in Code Pipeline for AWS:

1. This is dependent on your buildspec.yml (there's an example in this repo) & your build.grade having the below lines of code to name your jar file according.\
`
bootJar {
	archiveFileName = 'application.jar'
}
`

1. Start by creating a code build and verify that your code gets built before taking the extra steps for the pipeline.
    
    - Project Configuration
        - Name the project
    - Source
        - Set Source Provider to Github
        - Set Repository to your repository
    - Environment
        - Managed image (selected)
        - Operating System: Ubuntu
        - Either create a new or use an existing build role (just name it)
    - Buildspec
        - Use a build spec file (use the build spec file we have in here) 
    - Artifacts
        - No Artifacts
    - Logs
        - Up to you  
        
1.  Assuming your code builds.  You can then hook this into code pipeline. Create a code pipeline.

    - Pipeline Settings
        - Pipeline Name: <project>-CP
        - New or Existing depending if you have made a pipeline(if its new it'll make the name)
    - Source 
        - Set Source Provider to Github
        - Connect to Github
        - Select your repository
        - Select Github webhooks.(You need admin privileges for organization projects for this to work, if you have an error about webhooks..its this)
    - Build
        - Build Provider: AWS Codebuild
        - Region: whatever region you made your CodeBuild previously in.
        - Select your Code Build projects name.
    - Deploy
        - Deploy Provider: AWS Elastic Beanstalk
        - Region: Select the region your Application is in 
        - Application name: Name of the application
        - Environment name: Select the name of the environment
    - Review
        - Review everything then click create pipeline.


### Entities
- [Building](./src/main/java/com/teamshort/rocks/YourPackageIsHere/model/Building.java)
- [Tenant](./src/main/java/com/teamshort/rocks/YourPackageIsHere/model/Tenant.java)

### Controllers
- [Auth Controller](./src/main/java/com/teamshort/rocks/YourPackageIsHere/controller/AuthController.java)
- [Building Controller](./src/main/java/com/teamshort/rocks/YourPackageIsHere/controller/BuildingController.java)
- [Tenant Controller](./src/main/java/com/teamshort/rocks/YourPackageIsHere/controller/TenantController.java)

### CRUD Repositories
- [Building Repository](./src/main/java/com/teamshort/rocks/YourPackageIsHere/repository/BuildingRepository.java)
- [Tenant Repository](./src/main/java/com/teamshort/rocks/YourPackageIsHere/repository/TenantRepository.java)

### License - MIT

