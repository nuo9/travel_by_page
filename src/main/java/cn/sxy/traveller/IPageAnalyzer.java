package cn.sxy.traveller;

import java.util.List;

public interface IPageAnalyzer<P, R> {

    int getPageCount(P page);

    List<R> analyze(P page);

}
