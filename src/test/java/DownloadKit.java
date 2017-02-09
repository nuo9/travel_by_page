import com.github.kevinsawicki.http.HttpRequest;

import java.net.URL;

public class DownloadKit {

    public static String download(String url) {
        try {
            return new HttpRequest(new URL(url), HttpRequest.METHOD_GET).uncompress(true).body();
        } catch (Exception e) {
            return null;
        }
    }

}
