package com.playground.bestbuy.stepdefs;


import com.playground.bestbuy.steps.ServicesSteps;
import com.playground.bestbuy.utils.TestUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.hamcrest.Matchers.*;

public class ServicesStepdefs {
    static String name = "Camera Recycling" + TestUtils.getRandomValue();
    static long servicesId;

    @Steps
    ServicesSteps servicesSteps;

    @When("^User sends a GET request to the services endpoint , they must get back a valid status code 200$")
    public void userSendsAGETRequestToTheServicesEndpointTheyMustGetBackAValidStatusCode() {
        servicesSteps.getAllServices().statusCode(200);
    }

    @When("^I create a new services by providing the information name$")
    public void iCreateANewServicesByProvidingTheInformationName() {
        servicesId = servicesSteps.createNewService(name).log().all().statusCode(201).extract().response()
                .body().jsonPath().getLong("id");
        System.out.println("Services ID is : " + servicesId);
    }

    @Then("^I verify the services is created$")
    public void iVerifyTheServicesIsCreated() {
        servicesSteps.getServiceById(servicesId).log().all().statusCode(200);
        System.out.println("Services id is : " + servicesId);
    }

    @When("^I send GET request to services endpoint with Id \"([^\"]*)\" , I should received the services information$")
    public void iSendGETRequestToServicesEndpointWithIdIShouldReceivedTheServicesInformation(String _servicesId) {
        servicesSteps.getServiceById(servicesId).log().all().statusCode(200);
    }

    @When("^I update a created services providing the new name$")
    public void iUpdateACreatedServicesProvidingTheNewName() {
        name = name + "_Changed";
        servicesSteps.updateServices(servicesId, name).statusCode(200);
    }

    @Then("^I verify the services is updated$")
    public void iVerifyTheServicesIsUpdated() {
        servicesSteps.getServiceById(servicesId).body("name", equalTo(name));
    }

    @When("^I delete a created services ,they must get back a valid status code  is 200$")
    public void iDeleteACreatedServicesTheyMustGetBackAValidStatusCodeIs() {
        servicesSteps.deleteServicesById(servicesId).statusCode(200);
    }

    @Then("^I verify the services is created is deleted$")
    public void iVerifyTheServicesIsCreatedIsDeleted() {
        servicesSteps.getServiceById(servicesId).statusCode(404);
    }
}
