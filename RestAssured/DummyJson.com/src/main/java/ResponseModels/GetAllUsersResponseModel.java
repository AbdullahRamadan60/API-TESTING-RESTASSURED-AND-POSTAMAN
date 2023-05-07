package ResponseModels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "users",
        "total",
        "skip",
        "limit"
})
public class GetAllUsersResponseModel {

    @JsonProperty("users")
    public List<User> users;
    @JsonProperty("total")
    public int total;
    @JsonProperty("skip")
    public int skip;
    @JsonProperty("limit")
    public int limit;

    public static class User
    {
        @JsonProperty("id")
        public int id;
        @JsonProperty("firstName")
        public String firstName;
        @JsonProperty("lastName")
        public String lastName;
        @JsonProperty("maidenName")
        public String maidenName;
        @JsonProperty("age")
        public int age;
        @JsonProperty("gender")
        public String gender;
        @JsonProperty("email")
        public String email;
        @JsonProperty("phone")
        public String phone;
        @JsonProperty("username")
        public String username;
        @JsonProperty("password")
        public String password;
        @JsonProperty("birthDate")
        public String birthDate;
        @JsonProperty("image")
        public String image;
        @JsonProperty("bloodGroup")
        public String bloodGroup;
        @JsonProperty("height")
        public Integer height;
        @JsonProperty("weight")
        public float weight;
        @JsonProperty("eyeColor")
        public String eyeColor;
        @JsonProperty("hair")
        public Hair hair;
        @JsonProperty("domain")
        public String domain;
        @JsonProperty("ip")
        public String ip;
        @JsonProperty("address")
        public Address address;
        @JsonProperty("macAddress")
        public String macAddress;
        @JsonProperty("university")
        public String university;
        @JsonProperty("bank")
        public Bank bank;
        @JsonProperty("company")
        public Company company;
        @JsonProperty("ein")
        public String ein;
        @JsonProperty("ssn")
        public String ssn;
        @JsonProperty("userAgent")
        public String userAgent;
    }

    public static class Hair
    {
        @JsonProperty("color")
        public String color;
        @JsonProperty("type")
        public String type;
    }

    public static class Address
    {
        @JsonProperty("address")
        public String address;
        @JsonProperty("city")
        public String city;
        @JsonProperty("coordinates")
        public Coordinates coordinates;
        @JsonProperty("postalCode")
        public String postalCode;
        @JsonProperty("state")
        public String state;
    }

    public static class Coordinates
    {
        @JsonProperty("lat")
        public float lat;
        @JsonProperty("lng")
        public float lng;
    }

    public static class Bank
    {
        @JsonProperty("cardExpire")
        public String cardExpire;
        @JsonProperty("cardNumber")
        public String cardNumber;
        @JsonProperty("cardType")
        public String cardType;
        @JsonProperty("currency")
        public String currency;
        @JsonProperty("iban")
        public String iban;
    }

    public static class Company
    {
        @JsonProperty("address")
        public Address address;
        @JsonProperty("department")
        public String department;
        @JsonProperty("name")
        public String name;
        @JsonProperty("title")
        public String title;
    }

}
