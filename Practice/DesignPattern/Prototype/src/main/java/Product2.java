public class Product2 implements Product {

    private String name;

    private String description;

    public Product2 Clone() throws CloneNotSupportedException {
        return (Product2) super.clone();
    }
}
