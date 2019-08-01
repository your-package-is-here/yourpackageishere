package com.teamshort.rocks.YourPackageIsHere.controller;

import com.teamshort.rocks.YourPackageIsHere.model.Tenant;
import com.teamshort.rocks.YourPackageIsHere.payload.ApiResponse;
import com.teamshort.rocks.YourPackageIsHere.payload.BuildingSummary;
import com.teamshort.rocks.YourPackageIsHere.payload.SendMessageRequest;
import com.teamshort.rocks.YourPackageIsHere.repository.BuildingRepository;
import com.teamshort.rocks.YourPackageIsHere.model.Building;
import com.teamshort.rocks.YourPackageIsHere.repository.TenantRepository;
import com.teamshort.rocks.YourPackageIsHere.security.BuildingPrincipal;
import com.teamshort.rocks.YourPackageIsHere.security.CurrentBuilding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BuildingController {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private TenantRepository tenantRepository;

    private static final Logger logger = LoggerFactory.getLogger(BuildingController.class);

    @GetMapping("/user/me")
    public BuildingSummary getCurrentBuilding(@CurrentBuilding BuildingPrincipal currentBuilding) {
        BuildingSummary buildingSummary = new BuildingSummary(currentBuilding.getId(), currentBuilding.getUsername(), currentBuilding.getName(), currentBuilding.getStreetaddress(), currentBuilding.getCity(), currentBuilding.getState(), currentBuilding.getZip(), currentBuilding.getEmail());
        return buildingSummary;
    }

    @PostMapping("/user/sendmessage")
    public ResponseEntity<?> sendEmail(@CurrentBuilding BuildingPrincipal currentBuilding, @Valid @RequestBody SendMessageRequest sendMessageRequest){
        //Get the manager object
        Optional<Building> manager = buildingRepository.findByUsername(currentBuilding.getUsername());

        //Do logic to find the appropriate user(s) to send the email too
        List<Tenant> tenants = getTenantHelper(sendMessageRequest.getFirstname(), sendMessageRequest.getLastname(), sendMessageRequest.getAptnum(), manager.get().getId());

        //This will send the email
        Boolean isSent = false;

        // for loop to send email to all the tenants in the apartment if first name and last name does not match.
        for(Tenant tenant: tenants){
            isSent = helperSendEmail(tenant, sendMessageRequest.getTrackingnumber());
            helperSendSMS(tenant, sendMessageRequest.getTrackingnumber());
        }

        return ResponseEntity.ok(new ApiResponse(isSent, createMessage(isSent, !tenants.isEmpty())));
    }

    // This method creates the success or error message
    private String createMessage(boolean isSent, boolean isTenants){
        String message;
        if(isSent){
            message = "Your Email has been sent.";
        }else{
            if(isTenants){
                message = "There was a problem sending the email.";
            }else{
                message = "No matching tenants were found.";
            }
        }
        return message;
    }

    public static Boolean helperSendEmail(Tenant receiver, String trackingnumber){
        // This address must be verified with Amazon SES.
        String FROM = "noreply@yourpackageishere.com";

        //If your account is still in the sandbox, this address must be verified.
        String TO = receiver.getEmail();

        // The subject line for the email.
        String SUBJECT = "Your Package is Here";

        // The HTML body for the email.
        String HTMLBODY = String.format("Hello %s,\nYour package(tracking number %s) has been received at the office.\n", receiver.getFirstname(), trackingnumber);

        // The email body for recipients with non-HTML email clients.
        String TEXTBODY = String.format("Hello %s,\nYour package(tracking number %s) has been received at the office.\n", receiver.getFirstname(), trackingnumber);

        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(TO))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(HTMLBODY))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(TEXTBODY)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(FROM);
            client.sendEmail(request);
            System.out.println("Email sent!");
            return true;
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
            return false;
        }
    }

    public void helperSendSMS(Tenant receiver, String trackingnumber){
        AmazonSNSClient snsClient = new AmazonSNSClient();
        String message = String.format("Hello %s,\nYour package(tracking number %s) has been received at the office.\n", receiver.getFirstname(), trackingnumber);
        String phoneNumber = "+1" + receiver.getPhonenum();
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        //<set SMS attributes>
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
    }

    // This function returns a List of all possible and appropriate tenants.
    public List<Tenant> getTenantHelper(String firstname, String lastname, String aptnum, long id){
        // Gets a list of tenants ignoring case on first and last name
        List<Tenant> tenants = tenantRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCaseAndBuildingId(firstname, lastname, id);
        // If there are matches on the first and last name
        if(!tenants.isEmpty()){
            // Checks to see if the list contains the correct apt number
            if(containsAptnum(tenants, aptnum)){
                // Returns the tenants whose first and last name and apt number match
                tenants = tenants
                        .stream()
                        .filter(tenant -> tenant.getAptnum().toLowerCase().equals(aptnum.toLowerCase()))
                        .collect(Collectors.toList());
                return tenants;
            }else {
                // Returns all tenants via first and last name is apt is not a match
                return tenants;
            }
        }else{
            // If we found not matches based on name go solely on apt number
            tenants = tenantRepository.findByAptnumIgnoreCaseAndBuildingId(aptnum, id);
        }
        // Return whatever tenants we have acquired.
        return tenants;
    }

    // This function filters and returns true or false if there is a tenant with a matching apt number
    public boolean containsAptnum(final List<Tenant> tenants, String aptnum){
        return tenants.stream().filter(tenant -> tenant.getAptnum().toLowerCase().equals(aptnum.toLowerCase())).findFirst().isPresent();
    }
}
