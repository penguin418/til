package product;

import util.ArrayUtil;

import java.nio.charset.Charset;

public class Utf8Txt implements Txt {
    private byte[] header;
    private byte[] body;

    public Utf8Txt(byte[] body, byte[] header) {
        this.body = body;
        this.header = header;
    }

    @Override
    public String getText() {
        return new String(ArrayUtil.joinArrays(header, body), Charset.defaultCharset());
    }
}
