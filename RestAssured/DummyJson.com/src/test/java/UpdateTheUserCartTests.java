import Helper.Helper;
import Requests.Requests;
import ResponseModels.GetTheUserCartResponseModel;
import ResponseModels.UpdateTheUserCartResponseModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UpdateTheUserCartTests
{
    Map<String,String> headers;
    Response updateUserCartResponse;

    UpdateTheUserCartResponseModel updateTheUserCartResponseModel;

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

        Helper.addAProductInANewCartAndGetThisCartData();   //To make a new cart and put a random product in it

        Helper.getANewRandomProductData();   // To get another new random product data

        updateUserCartResponse = Requests.updateACartWithANewProduct(headers , Helper.cartId , Helper.newRandomProductId , true);

        updateTheUserCartResponseModel = updateUserCartResponse.as(UpdateTheUserCartResponseModel.class);

        updateUserCartResponse.prettyPrint();

        //System.out.println("UserId = " + Helper.randomId + " .CartId = " + Helper.cartId + " .ProductId = " + Helper.randomProductId + " .Newly random product id = " + Helper.newRandomProductId);

    }

    @Test
    public void statusCodeIs200()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updateUserCartResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        updateUserCartResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\updateTheUserCartSchema.json")));
    }

    @Test
    public void theCartIdIsTheSameCartIdWhichTheSelectedProductIsPuttedIn()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Integer.parseInt(updateTheUserCartResponseModel.id) , Helper.cartId);
        softAssert.assertAll();
    }

    @Test
    public void theCartIsBelongingToTheUser()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updateTheUserCartResponseModel.userId , Helper.randomId);
        softAssert.assertAll();
    }

    @Test
    public void theNewlySelectedProductIsInStock()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(updateTheUserCartResponseModel.products.get(0).quantity <= Helper.newRandomProductStock);
        softAssert.assertAll();
    }

    @Test
    public void cartContainsTheNewlySelectedProduct()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updateTheUserCartResponseModel.products.get(0).id , Helper.newRandomProductId);
        softAssert.assertEquals(updateTheUserCartResponseModel.products.get(0).title , Helper.newRandomProductTitle);
        softAssert.assertEquals(updateTheUserCartResponseModel.products.get(0).price , Helper.newRandomProductPrice);
        softAssert.assertAll();
    }

    @Test
    public void cartStillContainsTheOldSelectedProduct()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updateTheUserCartResponseModel.products.get(1).id , Helper.randomProductId);
        softAssert.assertEquals(updateTheUserCartResponseModel.products.get(1).title , Helper.randomProductTitle);
        softAssert.assertEquals(updateTheUserCartResponseModel.products.get(1).price , Helper.randomProductPrice);
        softAssert.assertAll();
    }

    @Test
    public void cartInfoAndPricesCalculationsAreUpdatedAndConsistent()
    {
        SoftAssert softAssert = new SoftAssert();

        // cart total price = the two products total prices
        softAssert.assertEquals(updateTheUserCartResponseModel.total , updateTheUserCartResponseModel.products.get(0).total + updateTheUserCartResponseModel.products.get(1).total );
        // cart discounted total =  the two products discounted prices
        softAssert.assertEquals(updateTheUserCartResponseModel.discountedTotal , updateTheUserCartResponseModel.products.get(0).discountedPrice + updateTheUserCartResponseModel.products.get(1).discountedPrice );
        // cart total product = product list size (which should be 2)
        softAssert.assertEquals(updateTheUserCartResponseModel.totalProducts , updateTheUserCartResponseModel.products.size());
        // cart total quantity = the two products quantities
        softAssert.assertEquals(updateTheUserCartResponseModel.totalQuantity , updateTheUserCartResponseModel.products.get(0).quantity + updateTheUserCartResponseModel.products.get(1).quantity );
        softAssert.assertAll();
    }




}
