package director;

import builder.FormatTxtBuilder;
import product.Txt;

public class Director {
    private FormatTxtBuilder builder;
    public void setBuilder(FormatTxtBuilder builder){
        this.builder = builder;
    }
    public Txt construct(String text){
        builder.setBody(text);
        return builder.getResult();
    }
}
