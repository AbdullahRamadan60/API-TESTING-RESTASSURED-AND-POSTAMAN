package ResponseModels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "carts",
        "total",
        "skip",
        "limit"
})
public class GetTheUserCartResponseModel
{
    @JsonProperty("carts")
    public List<Cart> carts;
    @JsonProperty("total")
    public int total;
    @JsonProperty("skip")
    public int skip;
    @JsonProperty("limit")
    public int limit;

    public static class Cart
    {
        @JsonProperty("id")
        public int id;
        @JsonProperty("products")
        public List<Product> products;
        @JsonProperty("total")
        public float total;
        @JsonProperty("discountedTotal")
        public float discountedTotal;
        @JsonProperty("userId")
        public int userId;
        @JsonProperty("totalProducts")
        public int totalProducts;
        @JsonProperty("totalQuantity")
        public int totalQuantity;

    }

    public static class Product
    {
        @JsonProperty("id")
        public int id;
        @JsonProperty("title")
        public String title;
        @JsonProperty("price")
        public float price;
        @JsonProperty("quantity")
        public int quantity;
        @JsonProperty("total")
        public float total;
        @JsonProperty("discountPercentage")
        public float discountPercentage;
        @JsonProperty("discountedPrice")
        public int discountedPrice;
    }



}
