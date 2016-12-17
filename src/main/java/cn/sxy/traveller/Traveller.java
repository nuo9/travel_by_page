package cn.sxy.traveller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

// P: page, R: result
public class Traveller<P, R> {

    private IContentProvider<P> contentProvider;
    private IPageAnalyzer<P, R> pageAnalyzer;

    private int retry = 3;
    private long retrySleep = 100;

    private Traveller() {
    }

    public static <P, R> Traveller<P, R> create(IContentProvider<P> contentProvider, IPageAnalyzer<P, R> pageAnalyzer) {
        Traveller<P, R> t = new Traveller<>();
        t.contentProvider = contentProvider;
        t.pageAnalyzer = pageAnalyzer;

        return t;
    }

    public Traveller setRetryAndSleep(int retry, int retrySleep) {
        this.retry = retry;
        this.retrySleep = retrySleep;
        return this;
    }

    public List<R> travel(int maxPage) {
        if (maxPage < 1)
            throw new IllegalArgumentException("maxPage illegal");

        P firstPage = getContent(1);
        if (firstPage == null) {
            return new ArrayList<>(0);
        }

        int pages = min(pageAnalyzer.getPageCount(firstPage), maxPage);

        List<R> results = new ArrayList<>(pageAnalyzer.analyze(firstPage));
        for (int i = 2; i <= pages; i++) {
            P content = getContent(i);
            if (content == null)
                continue;

            List<R> result = pageAnalyzer.analyze(content);
            results.addAll(result);
        }

        return results;
    }

    private P getContent(int page) {
        int count = 0;

        while (count < retry) {
            try {
                return contentProvider.get(page);
            } catch (IOException e) {
                count++;
            }

            try {
                Thread.sleep(retrySleep);
            } catch (InterruptedException ignored) {
            }
        }

        return null;
    }

}
