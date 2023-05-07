import Helper.Helper;
import Requests.Requests;
import ResponseModels.DeleteTheUserCartResponseModel;
import ResponseModels.UpdateTheUserCartResponseModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DeleteTheUserCartTests
{
    Map<String,String> headers;
    Response updateUserCartResponse;
    Response deleteUserCartResponse;

    DeleteTheUserCartResponseModel deleteTheUserCartResponseModel;

    String username;
    String password;

    @BeforeClass
    public void init()
    {
        //username & password to login
        //username = "atuny0";
        //password = "9uQFF1Lh";

        headers = new HashMap<>();
        //headers.put("Authorization", "Bearer " + Helper.loginAndGetAToken(username,password));
        //OR Login using a random user and get its token
        headers.put("Authorization", "Bearer " + Helper.loginAndGetARandomUserToken());  // random user is logging in

        Helper.getACartWithTwoProducts();  // To make a cart and put two random products in it

        deleteUserCartResponse = Requests.deleteACart(headers , Helper.cartId);

        deleteTheUserCartResponseModel = deleteUserCartResponse.as(DeleteTheUserCartResponseModel.class);

        deleteUserCartResponse.prettyPrint();

        System.out.println("UserId = " + Helper.randomId + " .CartId = " + Helper.cartId + " .ProductId = " + Helper.randomProductId + " .Newly random product id = " + Helper.newRandomProductId);

    }

    @Test
    public void statusCodeIs200()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deleteUserCartResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        deleteUserCartResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\deleteTheUserCartSchema.json")));
    }

    @Test
    public void theCartIsCorrect()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deleteTheUserCartResponseModel.id , Helper.cartId);
        softAssert.assertAll();
    }

    @Test
    public void theCartIsBelongingToTheUser()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deleteTheUserCartResponseModel.userId , Helper.randomId);
        softAssert.assertAll();
    }

    @Test
    public void cartContainsTheTwoSelectedProducts()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deleteTheUserCartResponseModel.products.get(0).id , Helper.newRandomProductId);
        softAssert.assertEquals(deleteTheUserCartResponseModel.products.get(1).id , Helper.randomProductId);
        softAssert.assertAll();
    }

    @Test
    public void cartIsDeletedSuccessfully()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(deleteTheUserCartResponseModel.isDeleted);
        softAssert.assertAll();
    }


}
