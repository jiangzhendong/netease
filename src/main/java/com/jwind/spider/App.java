package com.jwind.spider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jwind.spider.api.HttpPost;
import com.jwind.spider.modal.SongInfo;
import com.jwind.spider.utils.FileReadAndWrite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class App 
{
    public static void main(String[] args) {
        try {
            String selete = FileReadAndWrite.readDataFromConsole("请选择1.歌单下载；2.指定歌曲下载：");
            if ("1".equals(selete)) {
                //第1步：获取用户每个歌单的详情
                String playId = FileReadAndWrite.readDataFromConsole("请输入歌单ID：");
                String rest = HttpPost.getPlayList(playId);
                JSONObject jsonObject = JSONObject.parseObject(rest);
                JSONObject playLists = jsonObject.getJSONObject("playlist");
                JSONArray tracks = playLists.getJSONArray("tracks");
                List<SongInfo> songInfos = new ArrayList<SongInfo>();
                for (Object track : tracks) {
                    JSONObject songs = JSONObject.parseObject(track.toString());

                    String uslRest = HttpPost.getUrls(songs.getString("id"));
                    JSONObject uslRestJson = JSONObject.parseObject(uslRest);
                    String url = uslRestJson.getJSONArray("data").getJSONObject(0).getString("url");
                    String type = uslRestJson.getJSONArray("data").getJSONObject(0).getString("type");
                    String br = uslRestJson.getJSONArray("data").getJSONObject(0).getString("br");
                    if (null == url || "".equals(url)) {
                        continue;
                    }
                    SongInfo songInfo = new SongInfo();
                    songInfo.setId(songs.getString("id"));
                    songInfo.setName(songs.getString("name"));
                    songInfo.setUrl(url);
                    songInfo.setBr(Integer.parseInt(br));
                    songInfo.setType(type);
                    songInfos.add(songInfo);

                }
                String savePath = "Music" + File.separator + playId;
                //第2步：下载文件
                for (SongInfo s : songInfos) {
                    HttpPost.downLoadFromUrl(s.getUrl(), s.getName(), savePath, s.getType());
                }
            } else {
                String id = FileReadAndWrite.readDataFromConsole("请输入歌曲ID：");
                String name = FileReadAndWrite.readDataFromConsole("请输入歌曲名称：");
                if ("".equals(name) || null == name) {
                    name = id;
                }
                String uslRest = HttpPost.getUrls(id);
                JSONObject uslRestJson = JSONObject.parseObject(uslRest);
                String url = uslRestJson.getJSONArray("data").getJSONObject(0).getString("url");
                String type = uslRestJson.getJSONArray("data").getJSONObject(0).getString("type");
                HttpPost.downLoadFromUrl(url, name, "./Music", type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
