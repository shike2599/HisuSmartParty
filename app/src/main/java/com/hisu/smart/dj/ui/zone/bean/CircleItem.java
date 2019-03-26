package com.hisu.smart.dj.ui.zone.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.zone.widget.ImageUtil;
import com.jaydenxiao.common.baseapp.AppCache;

import java.util.ArrayList;
import java.util.List;

/**
 * des:说说实体类
 * Created by xsf
 * on 2016.07.11:11
 */
public class CircleItem implements Parcelable {

    private String address;
    private String appointUserNickname;
    private int appointUserid;
    private String content;
    private String createTime;
    private int goodjobCount;
    private int id;
    private String isvalid;
    private double latitude;
    private double longitude;
    private List<String> pictures = new ArrayList<>();
    private int replyCount;
    private int type;
    private String icon;
    private String userId;
    private String nickName;
    private List<FavortItem> goodjobs = new ArrayList<>();
    private List<CommentItem> replys = new ArrayList<>();
    private String linkImg;
    private String linkTitle;
    private int takeTimes;//接单总数

    public int getTakeTimes() {
        return takeTimes;
    }

    public void setTakeTimes(int takeTimes) {
        this.takeTimes = takeTimes;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppointUserNickname() {
        return appointUserNickname;
    }

    public void setAppointUserNickname(String appointUserNickname) {
        this.appointUserNickname = appointUserNickname;
    }

    public int getAppointUserid() {
        return appointUserid;
    }

    public void setAppointUserid(int appointUserid) {
        this.appointUserid = appointUserid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getGoodjobCount() {
        return goodjobCount;
    }

    public void setGoodjobCount(int goodjobCount) {
        this.goodjobCount = goodjobCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }


    public void addPictures(String image) {
        pictures.add(image);
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<FavortItem> getGoodjobs() {
        return goodjobs;
    }

    public void setGoodjobs(List<FavortItem> goodjobs) {
        this.goodjobs = goodjobs;
    }

    public List<CommentItem> getReplys() {
        return replys;
    }

    public void setReplys(List<CommentItem> replys) {
        this.replys = replys;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public CircleItem() {
    }

    public String getCurUserFavortId() {
        String userId = "";
        String myId = AppConfig.getInstance().getInt(AppConstant.USER_ID, -1) + "";
        if (goodjobs != null && !TextUtils.isEmpty(myId) && goodjobs.size() > 0) {
            for (FavortItem item : goodjobs) {
                if (myId.equals(item.getUserId())) {
                    userId = item.getUserId();
                    return userId;
                }
            }
        }
        return userId;
    }


    public int getCurFavortId() {
        int id = 0;
        String myId = AppConfig.getInstance().getInt(AppConstant.USER_ID, -1) + "";
        if (goodjobs != null && !TextUtils.isEmpty(myId) && goodjobs.size() > 0) {
            for (FavortItem item : goodjobs) {
                if (myId.equals(item.getUserId())) {
                    id = Integer.parseInt(item.getId());
                    return id;
                }
            }
        }
        return id;
    }


    /**
     * 获取图片链接
     */
    public List<String> getPictureList() {
        return pictures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.appointUserNickname);
        dest.writeInt(this.appointUserid);
        dest.writeString(this.content);
        dest.writeString(this.createTime);
        dest.writeInt(this.goodjobCount);
        dest.writeInt(this.id);
        dest.writeString(this.isvalid);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeStringList(this.pictures);
        dest.writeInt(this.replyCount);
        dest.writeInt(this.type);
        dest.writeString(this.icon);
        dest.writeString(this.userId);
        dest.writeString(this.nickName);
        dest.writeTypedList(goodjobs);
        dest.writeTypedList(replys);
        dest.writeString(this.linkImg);
        dest.writeString(this.linkTitle);
        dest.writeInt(this.takeTimes);
    }

    protected CircleItem(Parcel in) {
        this.address = in.readString();
        this.appointUserNickname = in.readString();
        this.appointUserid = in.readInt();
        this.content = in.readString();
        this.createTime = in.readString();
        this.goodjobCount = in.readInt();
        this.id = in.readInt();
        this.isvalid = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.pictures = in.createStringArrayList();
        this.replyCount = in.readInt();
        this.type = in.readInt();
        this.icon = in.readString();
        this.userId = in.readString();
        this.nickName = in.readString();
        this.goodjobs = in.createTypedArrayList(FavortItem.CREATOR);
        this.replys = in.createTypedArrayList(CommentItem.CREATOR);
        this.linkImg = in.readString();
        this.linkTitle = in.readString();
        this.takeTimes = in.readInt();
    }

    public static final Creator<CircleItem> CREATOR = new Creator<CircleItem>() {
        @Override
        public CircleItem createFromParcel(Parcel source) {
            return new CircleItem(source);
        }

        @Override
        public CircleItem[] newArray(int size) {
            return new CircleItem[size];
        }
    };
}
