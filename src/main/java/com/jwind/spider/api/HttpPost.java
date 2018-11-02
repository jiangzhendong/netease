package com.jwind.spider.api;

import com.jwind.spider.secret.JSSecret;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by J.wind
 * At 2018/10/25 8:25 AM
 * Email: jiangzd102@outlook.com
 */
public class HttpPost {
    //用户请求池
    private static String[] User_Agent = {
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
            "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Avant Browser)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"
    };


    /**
     * 根据ID获取音乐列表
     * 1982383224
     * 73289068
     *
     * @param uid
     * @return
     */
    public static String getUserMusicList(String uid) {
        try {
            UrlParamPair upp = Api.getPlaylistOfUser(uid);
            String req_str = upp.getParas().toJSONString();
            String userAgent = getUserAgent();
            Connection.Response
                    response = Jsoup.connect("https://music.163.com/weapi/user/playlist?csrf_token=")
                    .userAgent(userAgent)
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 每个歌单的歌曲列表
     *
     * @param commentThreadId
     * @return
     */
    public static String getPlayList(String commentThreadId) {
        try {
            UrlParamPair upp = Api.getDetailOfPlaylist(commentThreadId);
            String req_str = upp.getParas().toJSONString();
            String userAgent = getUserAgent();
            Connection.Response
                    response = Jsoup.connect("https://music.163.com/weapi/v3/playlist/detail?csrf_token=")
                    .userAgent(userAgent)
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取 每个歌单的歌曲列表
     *
     * @param songs
     * @return
     */
    public static String getUrls(String songs) {
        try {
            UrlParamPair upp = Api.getMusicURL(songs);
            String req_str = upp.getParas().toJSONString();
            String userAgent = getUserAgent();
            Connection.Response
                    response = Jsoup.connect("https://music.163.com/weapi/song/enhance/player/url?csrf_token=")
                    .userAgent(userAgent)
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-US;q=0.7")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     */
    public static String downLoadFromUrl(String urlStr, String fileName, String savePath, String type) {
        try {
            URL url = new URL(urlStr);
            //设置代理地址
//            System.setProperty("http.proxyHost", "223.223.187.195");
//            System.setProperty("http.proxyPort", "80");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", getUserAgent());
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            File file = new File(saveDir + File.separator + fileName + "." + type);
            try {
                if (!file.exists()) {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(getData);
                    fos.close();
                }
            } catch (IOException io) {
                file = new File(saveDir + File.separator + fileName.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", "") + "." + type);
                if (!file.exists()) {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(getData);
                    fos.close();
                }
            } finally {
                inputStream.close();
            }
            System.out.println("info:" + url + " download success");
            return "info:" + url + " download success";
        } catch (Exception e) {
            System.out.println(fileName + ":下载出错了！");
            e.printStackTrace();
        }
        return fileName + ":下载出错了！";
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 随机获取一个请求头部
     *
     * @return 随机的字符串
     */
    private static String getUserAgent() {
        //产生0-(arr.length-1)的整数值,也是数组的索引
        int index = (int) (Math.random() * User_Agent.length);
        return User_Agent[index];
    }
}
