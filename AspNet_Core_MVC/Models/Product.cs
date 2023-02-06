using eCommerce.Models;

namespace eCommerce.Models
{
    public class Product
    {
        public int ProductId { get; set; }

        public string Title { get; set; }

        public decimal Price { get; set; }

        public string ImageSourceUrl { get; set; }
    }

    public class ProductDetail
    {
        public int ProductId { get; set; }

        public string Title { get; set; }

        public decimal Price { get; set; }

        public string ImageSourceUrl { get; set; }

        public List<mcs_ProductPhoto> ProductPhotos { get; set; }
    }
}
