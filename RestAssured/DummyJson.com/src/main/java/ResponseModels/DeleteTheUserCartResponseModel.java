package ResponseModels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "products",
        "total",
        "discountedTotal",
        "userId",
        "totalProducts",
        "totalQuantity",
        "isDeleted",
        "deletedOn"
})
public class DeleteTheUserCartResponseModel
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
    @JsonProperty("isDeleted")
    public boolean isDeleted;
    @JsonProperty("deletedOn")
    public String deletedOn;

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
        public float discountedPrice;
    }

}
