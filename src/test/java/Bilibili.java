import cn.sxy.traveller.Traveller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.util.List;

public class Bilibili {

    public static void main(String[] args) {
        String url = "http://space.bilibili.com/ajax/fav/getList?mid=8706994&pagesize=1000000&fid=683151&pid=";

        Traveller<JSONObject, Object> t = Traveller.create(
                pageNum -> JSON.parseObject(DownloadKit.download(url + String.valueOf(pageNum)))
                , page -> (int) JSONPath.compile("$.data.pages").eval(page)
                , page -> (List) JSONPath.compile("$.data.pages").eval(page));
        List<Object> ss = t.travel(1);
        System.out.println(ss);
    }

}
