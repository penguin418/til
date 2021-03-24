import factory.ShapeFactory;
import factory.ShapeType;
import product.Circle;
import product.Shape;
import product.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    // 어째 1급 클래스처럼 보이네..
    static class Window1 {
        private final List<Shape> shapes = new ArrayList<>();

        public Window1(List<ShapeType> shapeTypes){
            for (ShapeType shapeType : shapeTypes){
                Shape shape = ShapeFactory.getInstance().getShape(shapeType);
                this.shapes.add(shape);
            }
        }

        public void draw(){
            for (Shape shape : this.shapes){
                shape.draw();
            }
        }
    }

    public static void main(String[] args){
        List<ShapeType> blueprint = Arrays.asList(ShapeType.Circle, ShapeType.Square);
        Window1 window1 = new Window1(blueprint);
        window1.draw();
    }
}
