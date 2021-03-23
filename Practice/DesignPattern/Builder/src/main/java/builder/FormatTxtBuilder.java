package builder;

import product.Txt;

public interface FormatTxtBuilder {
    void getReady();
    void setBody(String value);
    Txt getResult();
}
