import Requests.Requests;
import ResponseModels.GetAllUsersResponseModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GetAllUsersTests
{
    Map<String,String> queryParams;
    Response getAllUsersResponse;
    GetAllUsersResponseModel getAllUsersResponseModel;


    @BeforeClass
    public void init()
    {
        queryParams = new HashMap<>();
        queryParams.put("limit","0");

        getAllUsersResponse = Requests.getAllUsersRequest(queryParams);

        getAllUsersResponseModel = getAllUsersResponse.as(GetAllUsersResponseModel.class);

    }

    @Test
    public void statusCodeIs200()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAllUsersResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        getAllUsersResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\getAllUsersSchema.json")));
    }

    @Test
    public void allUsersAreListed()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getAllUsersResponseModel.users.size(),getAllUsersResponseModel.total,"Not all users are listed");
        softAssert.assertAll();
    }



}
