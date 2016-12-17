package cn.sxy.traveller;

import java.io.IOException;

public interface IContentProvider<P> {

    P get(int page) throws IOException;

}
