import Helper.Helper;
import Requests.Requests;
import ResponseModels.GetAllProductsResponseModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GetAllProductsTests
{
    Map<String,String> queryParams;
    Map<String,String> headers;
    Response getAllProductsResponse;
    GetAllProductsResponseModel getAllProductsResponseModel;
    String username;
    String password;

    @BeforeClass
    public void init()
    {
        queryParams = new HashMap<>();
        queryParams.put("limit","0");

        //username & password to login
        username = "atuny0";
        password = "9uQFF1Lh";

        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Helper.loginAndGetAToken(username,password));
        //OR Login using a random user and get its token
        //headers.put("Authorization", "Bearer " + Helper.loginAndGetARandomUserToken());

        getAllProductsResponse = Requests.getAllProductsRequest(queryParams,headers);

        getAllProductsResponseModel = getAllProductsResponse.as(GetAllProductsResponseModel.class);

    }

    @Test
    public void statusCodeIs200()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAllProductsResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        getAllProductsResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\getAllProductsSchema.json")));
    }

    @Test
    public void allProductsAreListed()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAllProductsResponseModel.products.size(),getAllProductsResponseModel.total,"Not all products are listed");
        softAssert.assertAll();
    }


}
