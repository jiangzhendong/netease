package com.jwind.spider.secret;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: J.wind
 * Date: 2018/7/25
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSSecret {
    private static Invocable inv;
    private static final String encText = "encText";
    private static final String encSecKey = "encSecKey";

    /**
     * 从本地加载修改后的 js 文件到 scriptEngine
     */
    static {
        try {
            InputStream is = JSSecret.class.getResourceAsStream("/core.js");
            byte[] bytes = toByteArray(is);
            String js = new String(bytes);
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("JavaScript");
            engine.eval(js);
            inv = (Invocable) engine;
            System.out.println("Init completed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ScriptObjectMirror get_params(String paras) throws Exception {
        return (ScriptObjectMirror) inv.invokeFunction("myFunc", paras);
    }

    public static HashMap<String, String> getDatas(String paras) {
        try {
            ScriptObjectMirror so = (ScriptObjectMirror) inv.invokeFunction("myFunc", paras);
            HashMap<String, String> datas = new HashMap<String, String>();
            datas.put("params", so.get(JSSecret.encText).toString());
            datas.put("encSecKey", so.get(JSSecret.encSecKey).toString());
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

}