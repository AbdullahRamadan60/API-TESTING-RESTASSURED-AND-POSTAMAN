import Helper.Helper;
import Requests.Requests;
import ResponseModels.GetTheUserCartResponseModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GetTheUserCartTests
{
    Map<String,String> queryParams;
    Map<String,String> headers;
    Response getUserCartResponse;

    GetTheUserCartResponseModel getTheUserCartResponseModel;

    String username;
    String password;

    @BeforeClass
    public void init()
    {
        queryParams = new HashMap<>();
        queryParams.put("limit","0");

        //username & password to login
        //username = "atuny0";
        //password = "9uQFF1Lh";

        headers = new HashMap<>();
        //headers.put("Authorization", "Bearer " + Helper.loginAndGetAToken(username,password));
        //OR Login using a random user and get its token
        headers.put("Authorization", "Bearer " + Helper.loginAndGetARandomUserToken());  // random user is logging in

        Helper.addAProductInANewCartAndGetThisCartData();   //To make a new cart and put a random product in it

        getUserCartResponse = Requests.getTheUserCart(queryParams,headers,Helper.randomId);

        getTheUserCartResponseModel = getUserCartResponse.as(GetTheUserCartResponseModel.class);

        getUserCartResponse.prettyPrint();

    }

    @Test
    public void statusCodeIs200()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getUserCartResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        getUserCartResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\getTheUserCartSchema.json")));
    }

    @Test
    public void theUserHasOnlyOneCart()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getTheUserCartResponseModel.carts.size() , 1);
        softAssert.assertAll();
    }

    @Test
    public void theCartIdIsTheSameCartIdWhichTheSelectedProductIsPuttedIn()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).id , Helper.cartId);
        softAssert.assertAll();
    }

    @Test
    public void theCartIsBelongingToTheUser()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).userId , Helper.randomId);
        softAssert.assertAll();
    }

    @Test
    public void cartInfoIsConsistentWithItsInfoWhenItWasCreated()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).total , Helper.cartTotal);
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).discountedTotal , Helper.cartDiscountedTotal);
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).totalProducts , Helper.cartTotalProducts);
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).totalQuantity , Helper.cartTotalQuantity);
        softAssert.assertAll();
    }

    @Test
    public void cartContainsTheSelectedProduct()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).products.get(0).id , Helper.randomProductId);
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).products.get(0).title , Helper.randomProductTitle);
        softAssert.assertEquals(getTheUserCartResponseModel.carts.get(0).products.get(0).price , Helper.randomProductPrice);
        softAssert.assertAll();
    }



}
