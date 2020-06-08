package muhammad.farhan.jfood_android;

public class Food {
    private int id;
    private String name;
    private int price;
    private String category;
    private Seller seller;


    public Food(int id, String name, Seller seller, int price, String category)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.seller = seller;
    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }
    public Seller getSeller ()
    {
        return seller;
    }
    public int getPrice()
    {
        return price;
    }
    public String getCategory()
    {
        return category;
    }



    public void setId (int Id)
    {
        this.id = Id;
    }
    public void setName (String name)
    {
        this.name = name;
    }
    public void setSeller (Seller seller)
    {
        this.seller = seller;
    }
    public void setPrice (int price)
    {
        this.price = price;
    }
    public void setCategory (String category)
    {
        this.category = category;
    }
}
