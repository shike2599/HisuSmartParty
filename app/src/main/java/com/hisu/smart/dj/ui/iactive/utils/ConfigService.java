package com.hisu.smart.dj.ui.iactive.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wdliveuc.android.domain.ConfigEntity;


import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * Created by lichee on 2019/2/14.
 */

public class ConfigService {

    public static final String PREFERENCE_NAME = "setting";
    private static ConfigEntity configEntity;

    public ConfigService() {
    }

    private static ConfigEntity getInstance() {
        if(configEntity == null) {
            configEntity = new ConfigEntity();
        }

        return configEntity;
    }

    private static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            class CpuFilter implements FileFilter {
                CpuFilter() {
                }

                @Override
                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }
            }

            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception var2) {
            var2.printStackTrace();
            return 1;
        }
    }

    public static ConfigEntity LoadConfig(Context context) {
        ConfigEntity configEntity = getInstance();
        int nCpuCores = getNumCores();
        SharedPreferences share = context.getSharedPreferences("setting", 0);
        configEntity.serverIp = share.getString("serverIp", context.getResources().getString(com.wdliveuc.android.ActiveMeeting7.R.string.imm_login_serverip_default));
        configEntity.userName = share.getString("userName", "");
        configEntity.passwordUser = share.getString("passwordUser", "");
        configEntity.passwordRoom = share.getString("passwordRoom", "");
        configEntity.enterpriseName = share.getString("enterpriseName", "demo");
        configEntity.roomID = share.getString("roomID", "");
        configEntity.notePath = share.getString("notePath", context.getResources().getString(com.wdliveuc.android.ActiveMeeting7.R.string.imm_note_path_default));
        configEntity.protocol = share.getInt("protocol", 1);
        configEntity.protocolsend = share.getInt("protocolsend", 0);
        configEntity.applyType = share.getInt("applyType", Integer.parseInt(context.getResources().getString(com.wdliveuc.android.ActiveMeeting7.R.string.imm_app_type)));
        configEntity.isLiveUC = share.getBoolean("isLiveUC", context.getResources().getBoolean(com.wdliveuc.android.ActiveMeeting7.R.bool.imm_isLiveUC));
        configEntity.isSavePassword = share.getBoolean("isSavePassword", true);
        configEntity.isDeleteCache = share.getBoolean("isDeleteCache", true);
        configEntity.max_rcvvideo = 4;
        configEntity.isShowVideoInfo = share.getBoolean("isShowVideoInfo", false);
        configEntity.isAECEnable = share.getInt("isAECEnable1", 0);
        configEntity.echoTime = share.getInt("echoTime", 80);
        configEntity.audioAlgorithm = share.getInt("audioAlgorithm1", 2);
        configEntity.previewSize = share.getString("previewSize", "640*480");
        configEntity.frameRate = share.getInt("frameRate", 15);
        configEntity.flow = share.getInt("flow", 384);
        configEntity.quality = share.getInt("quality", 1);
        configEntity.videoCompact = share.getInt("vidoCompact", nCpuCores >= 2?1:0);
        configEntity.hardware_expedite = share.getInt("hardware_expedite", 0);
        configEntity.isAutoLogin = share.getBoolean("isAutologin", false);
        ConfigEntity.isFirstOpenApp = share.getBoolean("isFirstOpenApp", true);
        configEntity.agree_type = share.getInt("agree_type", 1);
        configEntity.m_nickName = share.getString("m_nickName", "");
        configEntity.open_hard_solution = share.getInt("open_hard_solution1", 1);
        return configEntity;
    }
    public static void SaveConfig(Context context, ConfigEntity configEntity) {
        SharedPreferences share = context.getSharedPreferences("setting", 0);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("serverIp", configEntity.serverIp);
        editor.putString("userName", configEntity.userName);
        editor.putString("passwordUser", configEntity.passwordUser);
        editor.putString("passwordRoom", configEntity.passwordRoom);
        editor.putString("enterpriseName", configEntity.enterpriseName);
        editor.putString("roomID", configEntity.roomID);
        editor.putString("notePath", configEntity.notePath);
        editor.putInt("protocol", configEntity.protocol);
        editor.putInt("protocolsend", configEntity.protocolsend);
        editor.putInt("applyType", configEntity.applyType);
        editor.putInt("max_rcvvideo", configEntity.max_rcvvideo);
        editor.putBoolean("isSavePassword", configEntity.isSavePassword);
        editor.putBoolean("isDeleteCache", configEntity.isDeleteCache);
        editor.putBoolean("isShowVideoInfo", configEntity.isShowVideoInfo);
        editor.putInt("isAECEnable1", configEntity.isAECEnable);
        editor.putInt("echoTime", configEntity.echoTime);
        editor.putInt("audioAlgorithm1", configEntity.audioAlgorithm);
        editor.putString("previewSize", configEntity.previewSize);
        editor.putInt("frameRate", configEntity.frameRate);
        editor.putInt("flow", configEntity.flow);
        editor.putInt("quality", configEntity.quality);
        editor.putInt("vidoCompact", configEntity.videoCompact);
        editor.putInt("hardware_expedite", configEntity.hardware_expedite);
        editor.putString("m_nickName", configEntity.m_nickName);
        editor.putBoolean("isAutologin", configEntity.isAutoLogin);
        editor.putBoolean("isFirstOpenApp", ConfigEntity.isFirstOpenApp);
        editor.putInt("open_hard_solution1", configEntity.open_hard_solution);
        editor.putBoolean("isLiveUC", configEntity.isLiveUC);
        editor.putInt("agree_type", configEntity.agree_type);
        editor.commit();
    }
}
