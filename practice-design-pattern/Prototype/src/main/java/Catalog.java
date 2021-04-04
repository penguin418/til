import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private Product[] products = new Product[] {
            new Product1(), new Product2(), new Product3()
    };

    public Product buy1(int no){
        if (no == 0){
            return new Product1();
        }else if (no == 1){
            return new Product2();
        }else if (no == 2) {
            return new Product3();
        }else return null;
    };

    public Product buy2(int no) throws CloneNotSupportedException {
        if (no < 0 || no > 3) return null;
        return this.products[no].Clone();
    }
}
