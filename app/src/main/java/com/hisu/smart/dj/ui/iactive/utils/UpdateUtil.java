package com.hisu.smart.dj.ui.iactive.utils;

import android.app.ProgressDialog;

import com.hisu.smart.dj.ui.iactive.vo.UpdateInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Administrator on 14-3-19.
 */
public class UpdateUtil {
    public static UpdateInfo getUpdateInfo(String url)  {
        InputStream in = null;
        try{
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setConnectTimeout(2000);
            con.setRequestMethod("GET");
            int code = con.getResponseCode();
            in = con.getInputStream();
            return getUpdateInfo(in);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in!=null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static UpdateInfo getUpdateInfo(InputStream in) throws Exception {
        UpdateInfo up = new UpdateInfo();
        BufferedReader bf=new BufferedReader(new InputStreamReader(in,"UTF-8"));  
        //最好在将字节流转换为字符流的时候 进行转码  
        StringBuffer buffer=new StringBuffer();  
        String line="";  
        while((line=bf.readLine())!=null){  
            buffer.append(line);  
        }  
          
       String update_info=buffer.toString();
       if(update_info==null||update_info.equals(""))
    	   return null;
       
       String update_infos[]=update_info.split("\\|");
       try {
    	   up.setVersion(update_infos[1]);
    	   up.setUrl(update_infos[3]);
    		   up.setUrl(up.getUrl()+"/1.apk");
		} catch (Exception e) {
			return null;
		}
       /* XmlPullParser xml = Xml.newPullParser();
        xml.setInput(in, "UTF-8");
        int type = xml.getEventType();
        while(type != XmlPullParser.END_DOCUMENT) {
            switch(type) {
                case XmlPullParser.START_TAG:
                    if("versionCode".equals(xml.getName())) {
                        up.setVersion(xml.nextText());
                    } else if("description".equals(xml.getName())) {
                        up.setDes(xml.nextText());
                    } else if("apkUrl".equals(xml.getName())) {
                        up.setUrl(xml.nextText());
                    }
                    break;
            }
            type = xml.next();
        }*/
        return up;
    }

    /**
     * apk下载
     * @param url 下载路径
     * @param path 保存路径
     * @param pd 进度条
     * @return
     * @throws Exception
     */
    public static File getDownFile(String url,String path,ProgressDialog pd) throws Exception {
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setReadTimeout(5000);
        con.setRequestMethod("GET");
        InputStream in = con.getInputStream();
        if(con.getResponseCode() == 200) {
            int total = con.getContentLength();//下载文件总大小
            pd.setMax(total);
            File file = new File(path);
            FileOutputStream out = new FileOutputStream(file);
            byte []b = new byte[1024];
            int len = 0;//5888334
            int piss = 0;
            while((len = in.read(b)) != -1) {
                out.write(b, 0, len);
                piss += len;
                pd.setProgress(piss);
                //Thread.sleep(50);
            }
            out.flush();
            out.close();
            in.close();
            return file;
        }
        return null;
    }
}
