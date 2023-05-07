package RequestModels;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "username",
        "password"
})
public class UserLoginRequestModel
{
    @JsonProperty("username")
    public String username;
    @JsonProperty("password")
    public String password;

}
