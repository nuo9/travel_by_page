import cn.sxy.traveller.IContentProvider;
import cn.sxy.traveller.IPageAnalyzer;
import cn.sxy.traveller.Traveller;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

public class Bilibili {

    public static void main(String[] args) {

        Traveller<String, Object> t = Traveller.create(new BilibiliProvider(), new BilibiliAnalyzer());
        List<Object> ss = t.travel(1);
        System.out.println(ss);
    }

    static class BilibiliProvider implements IContentProvider<String> {

        @Override
        public String get(int page) throws IOException {
            return DownloadKit.download("http://space.bilibili.com/ajax/fav/getList?mid=8706994&pagesize=1000000&fid=683151&pid=" + String.valueOf(page));
        }

    }

    static class BilibiliAnalyzer implements IPageAnalyzer<String, Object> {

        @Override
        public int getPageCount(String page) {
            return JSON.parseObject(page).getJSONObject("data").getInteger("pages");
        }

        @Override
        public List<Object> analyze(String page) {
            return JSON.parseObject(page).getJSONObject("data").getJSONArray("vlist");
        }

    }

}
