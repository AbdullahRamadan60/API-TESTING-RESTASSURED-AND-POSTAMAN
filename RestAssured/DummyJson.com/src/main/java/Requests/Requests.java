package Requests;

import Helper.Helper;
import RequestModels.AddAProductInANewCartRequestModel;
import RequestModels.UpdateTheUserCartRequestModel;
import RequestModels.UserLoginRequestModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.LinkedList;
import java.util.Map;

public class Requests
{
    static String baseUrl = "https://dummyjson.com";
    static String usersEndpoint = "/users";
    static String loginEndPoint = "/auth/login";
    static String productsEndpoint = "/products";
    static String searchEndpoint = "/search";
    static String addAProductInANewCartEndPoint = "/carts/add";
    static String userCartEndPoint = "/carts/user/";
    static String cartEndpoint = "/carts/";



    public static Response getAllUsersRequest(Map<String,String> queryParams)
    {
        Response response = RestAssured.given().log().all()
                .queryParams(queryParams)
                .when()
                .get(baseUrl + usersEndpoint);
        return response;
    }

    public static Response userLoginRequest(String username, String password)
    {
        UserLoginRequestModel userLoginRequestModel = new UserLoginRequestModel();
        userLoginRequestModel.username = username;
        userLoginRequestModel.password = password;


        String payload = Helper.getObjectAsString(userLoginRequestModel);

        Response response = RestAssured.given().log().all()
                .contentType("application/json")
                .body(payload)
                .when()
                .post(baseUrl + loginEndPoint);
        return response;
    }

    public static Response getAllProductsRequest(Map<String,String> queryParams, Map<String,String> headers )
    {
        Response response = RestAssured.given().log().all()
                .queryParams(queryParams)
                .headers(headers)
                .when()
                .get(baseUrl + productsEndpoint);
        return response;
    }

    public static Response searchForProductsRequest(Map<String,String> queryParams, Map<String,String> headers )
    {
        Response response = RestAssured.given().log().all()
                .queryParams(queryParams)
                .headers(headers)
                .when()
                .get(baseUrl + productsEndpoint + searchEndpoint);
        return response;
    }

    public static Response addAProductInANewCartRequest(Map<String,String> headers, int userId, int productId)
    {

        AddAProductInANewCartRequestModel addAProductInANewCartRequestModel = new AddAProductInANewCartRequestModel();
        addAProductInANewCartRequestModel.userId = userId;

        AddAProductInANewCartRequestModel.Product product = new AddAProductInANewCartRequestModel.Product();
        product.id = productId;
        product.quantity = 1;

        addAProductInANewCartRequestModel.products = new LinkedList<>();
        addAProductInANewCartRequestModel.products.add(0,product);

        String payload = Helper.getObjectAsString(addAProductInANewCartRequestModel);

        Response response = RestAssured.given().log().all()
                .headers(headers)
                .contentType("application/json")
                .body(payload)
                .when()
                .post(baseUrl + addAProductInANewCartEndPoint);
        return response;
    }


    public static Response getTheUserCart(Map<String,String> queryParams,Map<String,String> headers, int userId)
    {

        Response response = RestAssured.given().log().all()
                .queryParams(queryParams)
                .headers(headers)
                .when()
                .pathParam("userId",userId)
                .get(baseUrl + userCartEndPoint + "{userId}");
        return response;
    }

    public static Response updateACartWithANewProduct(Map<String,String> headers, int cartId, int productId, boolean doMerge)
    {
        UpdateTheUserCartRequestModel updateTheUserCartRequestModel = new UpdateTheUserCartRequestModel();
        updateTheUserCartRequestModel.merge = doMerge;

        UpdateTheUserCartRequestModel.Product product = new UpdateTheUserCartRequestModel.Product();
        product.id = productId;
        product.quantity = 1;

        updateTheUserCartRequestModel.products = new LinkedList<>();
        updateTheUserCartRequestModel.products.add(product);

        String payload = Helper.getObjectAsString(updateTheUserCartRequestModel);

        Response response = RestAssured.given().log().all()
                .headers(headers)
                .pathParam("cartId",cartId)
                .contentType("application/json")
                .body(payload)
                .when()
                .put(baseUrl + cartEndpoint + "{cartId}");
        return response;
    }


    public static Response deleteACart(Map<String,String> headers, int cartId)
    {
        Response response = RestAssured.given().log().all()
                .headers(headers)
                .pathParam("cartId",cartId)
                .when()
                .delete(baseUrl + cartEndpoint + "{cartId}");
        return response;
    }


}
