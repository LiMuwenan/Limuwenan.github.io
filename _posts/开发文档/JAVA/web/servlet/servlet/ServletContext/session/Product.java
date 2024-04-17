package lib;

public class Product {
    private String id;
    private String name;
    private String description;
    private int stack;
    private double price;

    public Product(){}
    public Product(String id,String name,String description,double price,int stack){
        this.id=id;
        this.name=name;
        this.description=description;
        this.stack=stack;
        this.price=price;
    }

    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return this.id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }

    public void setStack(int stack){
        this.stack=stack;
    }
    public int getStack(){
        return this.stack;
    }

    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return this.price;
    }
}