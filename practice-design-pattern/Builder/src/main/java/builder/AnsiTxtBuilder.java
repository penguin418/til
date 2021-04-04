package builder;

import product.AnsiTxt;
import product.Txt;
import product.Utf8Txt;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AnsiTxtBuilder implements FormatTxtBuilder {
    private byte[] body;

    @Override
    public void getReady() {
        this.body = null;
    }

    @Override
    public void setBody(String value) {
        this.body = value.getBytes(Charset.defaultCharset());
    }

    @Override
    public Txt getResult() {
        return new AnsiTxt(body);
    }
}
