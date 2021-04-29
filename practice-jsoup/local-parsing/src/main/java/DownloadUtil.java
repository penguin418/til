import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;

public class DownloadUtil {
    private final static int BUFFER_SIZE = 1024;

    public static String absolutePath(String path)
    {
        return Paths.get(Paths.get("").toAbsolutePath().toString(), path).toString();
    }

    private static String getCurrentDir() {
        return Paths.get("").toAbsolutePath().toString();
    }

    public static boolean download(URL url, String filePath) {
        try ( // auto-closable
              ReadableByteChannel urlReadChannel = Channels.newChannel(url.openStream());
              FileOutputStream fileOutStream = new FileOutputStream(filePath);
              FileChannel fileChannel = fileOutStream.getChannel();
        ) { // try
            return fileChannel.transferFrom(urlReadChannel, 0, Long.MAX_VALUE) > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
