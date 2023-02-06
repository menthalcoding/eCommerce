namespace eCommerce.Models
{
    public class Basket
    {
        public int CartId { get; set; }

        public List<BasketItem> BasketItemList { get; set; }
    }

    public class BasketItem
    {
        public int ProductId { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }

        public int Quantity { get; set; }

        public decimal Price { get; set; }

        public string ImageUri { get; set; }
    }

    public class BasketItemQuantity
    {   
        public int ProductId { get; set; }

        public int Quantity { get; set; }
    }
}
