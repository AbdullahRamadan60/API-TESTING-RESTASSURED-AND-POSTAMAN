package ResponseModels;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "products",
        "total",
        "skip",
        "limit"
})
public class GetAllProductsResponseModel
{
    @JsonProperty("products")
    public List<Product> products;
    @JsonProperty("total")
    public int total;
    @JsonProperty("skip")
    public int skip;
    @JsonProperty("limit")
    public int limit;

    public static class Product
    {
        @JsonProperty("id")
        public int id;
        @JsonProperty("title")
        public String title;
        @JsonProperty("description")
        public String description;
        @JsonProperty("price")
        public float price;
        @JsonProperty("discountPercentage")
        public float discountPercentage;
        @JsonProperty("rating")
        public float rating;
        @JsonProperty("stock")
        public int stock;
        @JsonProperty("brand")
        public String brand;
        @JsonProperty("category")
        public String category;
        @JsonProperty("thumbnail")
        public String thumbnail;
        @JsonProperty("images")
        public List<String> images;
    }

}
