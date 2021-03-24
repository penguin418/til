package factory;

import product.Circle;
import product.Shape;
import product.Square;

/**
 * Abstract Factory
 */
public class ShapeFactory {
    private static ShapeFactory instance;

    private ShapeFactory(){
    }

    public static ShapeFactory getInstance(){
        if (instance == null) instance = new ShapeFactory();
        return instance;
    }

    public final Shape getShape(ShapeType shapeType){
        switch (shapeType){
            case Circle:return new Circle();
            case Square:return new Square();
        }
        return null;
    }
}
