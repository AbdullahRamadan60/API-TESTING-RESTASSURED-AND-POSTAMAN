import Helper.Helper;
import Requests.Requests;
import ResponseModels.UserLoginResponseModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.File;


public class UserLoginTests
{
    Response userLoginResponse;
    UserLoginResponseModel userLoginResponseModel;
    String userName;
    String password;

    @BeforeClass
    public void init()
    {
        //Helper.getARandomUserData();  // Sending (get all users request) & Getting a random user data to be used in the next login request
        //userName = Helper.randomUserName;
        //password = Helper.randomPassword;
        //OR
        userName = "atuny0";
        password = "9uQFF1Lh";

        userLoginResponse = Requests.userLoginRequest(userName, password);

        userLoginResponseModel = userLoginResponse.as(UserLoginResponseModel.class);

    }

    @Test
    public void statusCodeIs200()
    {
        userLoginResponse.prettyPrint();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(userLoginResponse.statusCode(),200,"UnExpected Status Code");
        softAssert.assertAll();
    }

    @Test
    public void validateResponseJsonSchema()
    {
        userLoginResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(".\\src\\main\\resources\\postUserLoginSchema.json")));
    }

    @Test
    public void validateTheAuthentication()
    {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(userLoginResponseModel.id,Helper.randomId,"Bad authentication. Wrong id");
        softAssert.assertEquals(userLoginResponseModel.username,Helper.randomUserName,"Bad authentication. Wrong username");

        softAssert.assertAll();
    }


}
