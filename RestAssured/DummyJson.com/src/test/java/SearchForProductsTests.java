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

public class SearchForProductsTests
{
    Map<String,String> queryParams;
    Map<String,String> headers;
    Response getSearchedProductsResponse;
    GetAllProductsResponseModel getSearchedProductsResponseModel;     //Using the same model of GetAllProductsResponse
    String searchKeyWord;
    String username;
    String password;

    @BeforeClass
    public void init()
    {
        searchKeyWord = "laptop";

        queryParams = new HashMap<>();
        queryParams.put("limit","0");
        queryParams.put("q",searchKeyWord.toLowerCase());

        //username & password to login
        username = "atuny0";
        password = "9uQFF1Lh";

        headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Helper.loginAndGetAToken(username,password));
        //OR Login using a random user and get its token
        //headers.put("Authorization", "Bearer " + Helper.loginAndGetARandomUserToken());

        getSearchedProductsResponse = Requests.searchForProductsRequest(queryParams,headers);

        getSearchedProductsResponseModel = getSearchedProductsResponse.as(GetAllProductsResponseModel.class);


    }

    @Test
    public void statusCodeIs200()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getSearchedProductsResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        getSearchedProductsResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\getAllProductsSchema.json")));
    }

    @Test
    public void theRelevantProductsAreListed()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getSearchedProductsResponseModel.products.size(),getSearchedProductsResponseModel.total,"Not all relevant products are listed");
        softAssert.assertTrue( Helper.doesKeyWordExsitInTheSearchedProducts(getSearchedProductsResponseModel,searchKeyWord) );
        softAssert.assertAll();
    }

}
