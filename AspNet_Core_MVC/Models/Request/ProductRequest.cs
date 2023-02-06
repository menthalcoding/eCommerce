namespace eCommerce.Models
{
    public class ProductRequest
    {
        public ProductRequest() { }

        public int? CategoryId { get; set; }

        public string? Sort { get; set; }

        public string? OrderDirection { get; set; }

        public string? Status { get; set; }
    }
}
