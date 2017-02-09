package cn.sxy.traveller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import static java.lang.Math.min;

// P: page, R: result
public class Traveller<P, R> {

    private Function<Integer, P> contentProvider;
    private Function<P, Integer> pageCountReader;
    private Function<P, Collection<R>> pageAnalyzer;

    private int retry = 3;
    private long retrySleep = 100;

    private Traveller() {
    }

    public static <P, R> Traveller<P, R> create(Function<Integer, P> contentProvider,
                                                Function<P, Integer> pageCountReader,
                                                Function<P, Collection<R>> pageAnalyzer) {
        Traveller<P, R> t = new Traveller<>();
        t.contentProvider = contentProvider;
        t.pageCountReader = pageCountReader;
        t.pageAnalyzer = pageAnalyzer;
        return t;
    }

    public Traveller setRetryAndSleep(int retry, int retrySleep) {
        this.retry = retry;
        this.retrySleep = retrySleep;
        return this;
    }

    public ArrayList<R> travel(int maxPage) {
        if (maxPage < 1)
            throw new IllegalArgumentException("maxPage illegal");

        P firstPage = getContent(1);
        if (firstPage == null) {
            return new ArrayList<>(0);
        }

        int pages = min(pageCountReader.apply(firstPage), maxPage);

        ArrayList<R> results = new ArrayList<>(pageAnalyzer.apply(firstPage));
        for (int i = 2; i <= pages; i++) {
            P content = getContent(i);
            if (content == null)
                continue;

            Collection<R> result = pageAnalyzer.apply(content);
            results.addAll(result);
        }

        return results;
    }

    private P getContent(int page) {
        int count = 0;

        while (count < retry) {
            try {
                return contentProvider.apply(page);
            } catch (Exception e) {
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
