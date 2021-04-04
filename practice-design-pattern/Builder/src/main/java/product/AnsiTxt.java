package product;

import java.nio.charset.Charset;

public class AnsiTxt implements Txt {
    private byte[] body;

    public AnsiTxt(byte[] body) {
        this.body = body;
    }

    @Override
    public String getText() {
        return new String(body, Charset.defaultCharset());
    }
}
