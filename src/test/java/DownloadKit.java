import com.github.kevinsawicki.http.HttpRequest;

import java.io.IOException;
import java.net.URL;

public class DownloadKit {

    public static String download(String url) throws IOException {
        return new HttpRequest(new URL(url), HttpRequest.METHOD_GET).uncompress(true).body();
    }

}
