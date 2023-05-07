import Helper.Helper;
import Requests.Requests;
import ResponseModels.AddAProductInNewCartResponseModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AddProductInCartTests
{
    Map<String,String> headers;
    Response addProductInCartResponse;
    AddAProductInNewCartResponseModel addAProductInNewCartResponseModel;
    int userId;
    int productId;

    @BeforeClass
    public void init()
    {
        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Helper.loginAndGetARandomUserToken());

        userId = Helper.randomId;

        Helper.getARandomProductData();    // Sending (get all products request) & Getting a random product data to be used in the next add to cart request
        productId = Helper.randomProductId;

        addProductInCartResponse = Requests.addAProductInANewCartRequest(headers,userId,productId);

        addAProductInNewCartResponseModel = addProductInCartResponse.as(AddAProductInNewCartResponseModel.class);

        addProductInCartResponse.prettyPrint();
    }

    @Test
    public void statusCodeIs200()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addProductInCartResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        addProductInCartResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\postAddAProductInANewCart.json")));
    }

    @Test
    public void theCartBelongToTheRightUser()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addAProductInNewCartResponseModel.userId , Helper.randomId);
        softAssert.assertAll();
    }

    @Test
    public void theProductIsInStock()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(addAProductInNewCartResponseModel.products.get(0).quantity <= Helper.randomProductStock);
        softAssert.assertAll();
    }

    @Test
    public void TheSelectedProductIsAddedSuccessfullyInTheNewCreatedCart()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(addAProductInNewCartResponseModel.products.get(0).id , Helper.randomProductId);
        softAssert.assertAll();
    }

    @Test
    public void validateProductPricesCalculationsAreConsistent()
    {
        SoftAssert softAssert = new SoftAssert();

        //product total price  = product unit price * quantity
        float productTotalPrice = addAProductInNewCartResponseModel.products.get(0).total;
        float productUnitPrice =  addAProductInNewCartResponseModel.products.get(0).price;
        int productQuantity = addAProductInNewCartResponseModel.products.get(0).quantity;

        softAssert.assertEquals(productTotalPrice ,productUnitPrice * productQuantity,"wrong product total price calculation");

        //product discounted price = product total price - (product total price * (productDiscountPercentage/100))

        float productDiscountedPrice = addAProductInNewCartResponseModel.products.get(0).discountedPrice;
        float productDiscountPercentage = addAProductInNewCartResponseModel.products.get(0).discountPercentage;

        softAssert.assertEquals( productDiscountedPrice,( productTotalPrice - (productTotalPrice * (productDiscountPercentage/100)) ) , "wrong product discounted price calculation");
        softAssert.assertAll();
    }

    @Test
    public void validateThatCartInfoIsConsistentWithProductInfo()
    {
        SoftAssert softAssert = new SoftAssert();

        //cart total price = product total price (Because it's only one product)
        softAssert.assertEquals(addAProductInNewCartResponseModel.total ,addAProductInNewCartResponseModel.products.get(0).total,"product-cart total prices mismatch");

        //cart discounted total = product discounted price    (Because it's only one product)
        softAssert.assertEquals(addAProductInNewCartResponseModel.discountedTotal ,addAProductInNewCartResponseModel.products.get(0).discountedPrice,"product-cart discounted prices mismatch");

        //total products in the cart = products list size
        softAssert.assertEquals(addAProductInNewCartResponseModel.totalProducts,addAProductInNewCartResponseModel.products.size(),"wrong cart total products");

        //cart total quantity = product quantity  (Because it's only one product)'
        softAssert.assertEquals(addAProductInNewCartResponseModel.totalQuantity,addAProductInNewCartResponseModel.products.get(0).quantity,"product-cart total quantity mismatch");

        softAssert.assertAll();
    }





}
