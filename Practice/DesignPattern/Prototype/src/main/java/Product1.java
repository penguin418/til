public class Product1 implements Product {

    private String name;

    private String description;

    public Product1 Clone() throws CloneNotSupportedException {
        return (Product1) super.clone();
    }
}
