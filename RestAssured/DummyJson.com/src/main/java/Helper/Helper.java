package Helper;

import Requests.Requests;
import ResponseModels.AddAProductInNewCartResponseModel;
import ResponseModels.GetAllProductsResponseModel;
import ResponseModels.GetAllUsersResponseModel;
import ResponseModels.UserLoginResponseModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Helper
{

    public static String getObjectAsString(Object object)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = "";

        try
        {
            payload = objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {throw new RuntimeException(e);}

        return payload;
    }

    public static boolean doesKeyWordExsitInTheSearchedProducts(GetAllProductsResponseModel getSearchedProductsResponseModel, String searchKeyWord)
    {
        boolean isSearchKeyWordExsit = false;

        // To check that all the appeared products titles or descriptions contain the search key word
        for (int i = 0 ; i < getSearchedProductsResponseModel.products.size() ; i++)
        {
            String title = getSearchedProductsResponseModel.products.get(i).title.toLowerCase();
            String desc = getSearchedProductsResponseModel.products.get(i).description.toLowerCase();

            if (title.contains(searchKeyWord) || desc.contains(searchKeyWord))
            {
                isSearchKeyWordExsit = true;
            }
            else
            {
                System.out.println("The error in the product of index " + i);
                isSearchKeyWordExsit = false;
                break;
            }
        }
        return isSearchKeyWordExsit;
    }


    public static String randomUserName;
    public static String randomPassword;
    public static int randomId;
    public static void getARandomUserData()   // used to get a random user data (id, username, password) from the all users list to be used in the login request
    {
        Map queryParams= new HashMap<String,String>();
        queryParams.put("limit","0");

        Response getAllUsersResponse = Requests.getAllUsersRequest(queryParams);
        GetAllUsersResponseModel getAllUsersResponseModel = getAllUsersResponse.as(GetAllUsersResponseModel.class);

        Random random = new Random();
        int randomIndex = random.nextInt(getAllUsersResponseModel.users.size());


        randomId = getAllUsersResponseModel.users.get(randomIndex).id;
        randomUserName = getAllUsersResponseModel.users.get(randomIndex).username;
        randomPassword = getAllUsersResponseModel.users.get(randomIndex).password;
    }

    public static String randomUserToken;
    public static String loginAndGetARandomUserToken()
    {
        //String randomUserToken;

        getARandomUserData();

        Response loginResponse = Requests.userLoginRequest(randomUserName,randomPassword);

        UserLoginResponseModel userLoginResponseModel = loginResponse.as(UserLoginResponseModel.class);

        randomUserToken = userLoginResponseModel.token;

        return randomUserToken;
    }

    public static String loginAndGetAToken(String userName, String password)
    {
        String userToken;

        Response loginResponse = Requests.userLoginRequest(userName,password);

        UserLoginResponseModel userLoginResponseModel = loginResponse.as(UserLoginResponseModel.class);

        userToken = userLoginResponseModel.token;

        return userToken;
    }



    public static int randomProductId;
    public static String randomProductTitle;
    public static float randomProductPrice;
    public static int randomProductStock;
    public static void getARandomProductData()   // used to get a random product data
    {
        Map queryParams= new HashMap<String,String>();
        queryParams.put("limit","0");

        Map headers= new HashMap<String,String>();
        headers.put("Authorization", "Bearer " + randomUserToken);

        Response getAllProductsResponse = Requests.getAllProductsRequest(queryParams,headers);

        GetAllProductsResponseModel getAllProductsResponseModel = getAllProductsResponse.as(GetAllProductsResponseModel.class);

        Random random = new Random();
        int randomIndex = random.nextInt(getAllProductsResponseModel.products.size());


        randomProductId = getAllProductsResponseModel.products.get(randomIndex).id;
        randomProductTitle = getAllProductsResponseModel.products.get(randomIndex).title;
        randomProductPrice = getAllProductsResponseModel.products.get(randomIndex).price;
        randomProductStock = getAllProductsResponseModel.products.get(randomIndex).stock;
    }


    public static int cartId;
    public static float cartTotal;
    public static float cartDiscountedTotal;
    public static int cartTotalProducts;
    public static int cartTotalQuantity;
    public static void addAProductInANewCartAndGetThisCartData()   // used to get the data of the newly created cart for a random user
    {
        getARandomProductData();

        Map headers= new HashMap<String,String>();
        headers.put("Authorization", "Bearer " + randomUserToken);


        Response addProductInCartResponse = Requests.addAProductInANewCartRequest(headers,randomId,randomProductId);

        AddAProductInNewCartResponseModel addAProductInNewCartResponseModel = addProductInCartResponse.as(AddAProductInNewCartResponseModel.class);


        cartId = addAProductInNewCartResponseModel.id;
        cartTotal = addAProductInNewCartResponseModel.total;
        cartDiscountedTotal = addAProductInNewCartResponseModel.discountedTotal;
        cartTotalProducts = addAProductInNewCartResponseModel.totalProducts;
        cartTotalQuantity = addAProductInNewCartResponseModel.totalQuantity;
    }



    public static int newRandomProductId;
    public static String newRandomProductTitle;
    public static float newRandomProductPrice;
    public static int newRandomProductStock;
    public static void getANewRandomProductData()   // used to get another new random product data
    {
        Map queryParams= new HashMap<String,String>();
        queryParams.put("limit","0");

        Map headers= new HashMap<String,String>();
        headers.put("Authorization", "Bearer " + randomUserToken);

        Response getAllProductsResponse = Requests.getAllProductsRequest(queryParams,headers);

        GetAllProductsResponseModel getAllProductsResponseModel = getAllProductsResponse.as(GetAllProductsResponseModel.class);

        Random random = new Random();
        int randomIndex = random.nextInt(getAllProductsResponseModel.products.size());


        newRandomProductId = getAllProductsResponseModel.products.get(randomIndex).id;
        newRandomProductTitle = getAllProductsResponseModel.products.get(randomIndex).title;
        newRandomProductPrice = getAllProductsResponseModel.products.get(randomIndex).price;
        newRandomProductStock = getAllProductsResponseModel.products.get(randomIndex).stock;
    }

    public static void  getACartWithTwoProducts()
    {
        Map headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + randomUserToken);  // random user is logging in

        Helper.addAProductInANewCartAndGetThisCartData();   //To make a new cart and put a random product in it

        Helper.getANewRandomProductData();   // To get another new random product data

        Requests.updateACartWithANewProduct(headers , Helper.cartId , Helper.newRandomProductId , true);  //The new random product is added

    }








}
