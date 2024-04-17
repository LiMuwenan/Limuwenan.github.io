package lib;

public class ShopCart {
    private String id;
    private String name;
    private String description;
    private int num;
    private double price;

    public ShopCart(){}
    public ShopCart(String id,String name,String description,double price,int num){
        this.id=id;
        this.name=name;
        this.description=description;
        this.num=num;
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

    public void setNum(int num){
        this.num=num;
    }
    public int getNum(){
        return this.num;
    }

    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return this.price;
    }

    private double totalPrice(){

        return 0;
    }
}