package blueteam.mypantry.core;

// @Nathan: technically packets are generic in the data they hold, but since we don't want the
//          hassle I have remove the generic packet interface and instead specialized it.

/*
    Rachel: Here is the categories list we came up with:
            Beverages
            Bread/Bakery
            Canned/Jarred Goods
            Dairy
            Dry/Baking Goods
            Frozen Food
            Meat/Seafood
            Produce
            Cleaners
            Paper Goods
            Personal Care
            Other.
*/

public class btProduct {
    public String Name;
    public String Category;
    public int Quantity;
    public float Price;
    public String PurchaseDate;
    public boolean Perishable;
    public String PerishDate;

    public boolean PendingSync;
}
