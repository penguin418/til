package builder;

import product.Txt;
import product.Utf8Txt;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Utf8TxtBuilder implements FormatTxtBuilder {
    private byte[] body;
    private byte[] header;

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
        return new Utf8Txt(body, header);
    }
}
