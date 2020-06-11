package com.playground.bestbuy.crudtest;

import com.playground.bestbuy.steps.StoresSteps;
import com.playground.bestbuy.testbase.TestBase;
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
public class StoresCRUDTest extends TestBase {

    static String name = "New store";
    static String type = "Box";
    static String address = "1,Millwards";
    static String address2 ="Hatfield";
    static String city ="London";
    static String state = "UK";
    static String zip = "AL10 8BB";
    static double lat = 19.99;
    static double lng =  -93.4423;
    static String hours = "Mon: 10-6, Tue: 8-5, Wed: 10-6, Thurs: 8-5, Fri: 8-6, Sat: 8-6" ;
    static long storeId;

    @Steps
    StoresSteps storesSteps;

    @Title("This test will create a new stores and verify its generated")
    @Test
    public void test001() {
        storeId = storesSteps.createNewStore(name, type, address, address2, city, state, zip, lat, lng, hours).log().all().extract().response()
                .body().jsonPath().getLong("id");
        System.out.println("store id is : " + storeId);
    }

    @Title("This test will get the stores information by ID")
    @Test
    public void test002() {
        storesSteps.getStoreById(storeId).statusCode(200);

    }

    @Title("This test will update the store information and verify the updated information")
    @Test

    public void test003() {


        name = name+"_changed";
        type = type+"_changed";
        address =address+"_updated";
        address2 = address2 +"_updated";
        hours = "Mon: 8-6; Tue: 8-6; Wed: 8-6; Thurs: 8-6; Fri: 8-6; Sat: 8-6; Sun: 8-6";

        storesSteps.updateStore(storeId,name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(200);
        storesSteps.getStoreById(storeId).body("name",equalTo(name));

    }

    @Title("This test will delete the store and verify the store is deleted ")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeId).statusCode(200);
        storesSteps.getStoreById(storeId).statusCode(404);
    }
}
