public class Product3 implements Product {

    private String name;

    private String description;

    public Product3 Clone() throws CloneNotSupportedException {
        return (Product3) super.clone();
    }
}
