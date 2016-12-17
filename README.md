# travel_by_page

一个小型的JAVA框架。
用来进行分页遍历某些数据，并将结果保存起来。   

比如要抓取某人哔站的收藏，接口链接是这样的（为了方便展示就把pagesize设成了2）
http://space.bilibili.com/ajax/fav/getList?mid=8706994&pagesize=2&fid=683151&pid=1   

要获取全部收藏，流程是：  
1.抓第一页数据，     
2.分析第一页数据，获取页码数，   
3.根据页码组成2~n页的链接，分别请求。   
4.将1~n页的结果保存起来。      

本框架干的就是这件事。
