package RequestModels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userId",
        "products"
})
public class AddAProductInANewCartRequestModel
{

    @JsonProperty("userId")
    public int userId;
    @JsonProperty("products")
    public List<Product> products;

    public static class Product
    {

        @JsonProperty("id")
        public int id;
        @JsonProperty("quantity")
        public int quantity;

    }

}
