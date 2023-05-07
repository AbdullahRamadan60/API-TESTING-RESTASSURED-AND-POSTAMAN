package ResponseModels;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "username",
        "email",
        "firstName",
        "lastName",
        "gender",
        "image",
        "token"
})
public class UserLoginResponseModel
{
    @JsonProperty("id")
    public int id;
    @JsonProperty("username")
    public String username;
    @JsonProperty("email")
    public String email;
    @JsonProperty("firstName")
    public String firstName;
    @JsonProperty("lastName")
    public String lastName;
    @JsonProperty("gender")
    public String gender;
    @JsonProperty("image")
    public String image;
    @JsonProperty("token")
    public String token;

}
