package com.playground.bestbuy.crudtest;

import com.playground.bestbuy.steps.ServicesSteps;
import com.playground.bestbuy.testbase.TestBase;
import com.playground.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServicesCRUDTest extends TestBase {
    static String name = "Surya Warehouse" + TestUtils.getRandomValue();
    static long servicesId;

    @Steps
    ServicesSteps servicesSteps;

    @Title("This test will create a new services and verify its generated")
    @Test
    public void test001(){

        servicesId = servicesSteps.createNewService(name).log().all()
                .statusCode(201).extract().response().body().jsonPath().getLong("id");
        System.out.println("Services ID is" + servicesId);


    }

    @Title("This test will get the service information by ID")
    @Test

    public void test002() {
        servicesSteps.getServiceById(servicesId).log().all().statusCode(200);

    }

    @Title("This test will update the services information and verify the updated information")
    @Test

    public void test003(){

        name = name+"_Changed";

        servicesSteps.updateServices(servicesId,name).statusCode(200);
        servicesSteps.getServiceById(servicesId).body("name",equalTo(name));

    }
    @Title("This test will delete the categories information and verify the categories is deleted ")
    @Test

    public void test004(){
        servicesSteps.deleteServicesById(servicesId).statusCode(200);
        servicesSteps.getServiceById(servicesId).statusCode(404);
    }

}
