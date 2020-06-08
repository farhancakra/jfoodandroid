package muhammad.farhan.jfood_android;

public class Seller {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;


    public Seller(int id, String name, String email,String phoneNumber, Location location)
    {
        this.id = id;
        this.name = name;
        this.phoneNumber=phoneNumber;
        this.email = email;
        this.location = location;
    }

    public int getId()
    {
        // put your code here
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }


    public Location getLocation()
    {
        return location;
    }


    public void setId(int Id)
    {
        this.id = Id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation (Location location)
    {
        this.location = location;
    }
}

