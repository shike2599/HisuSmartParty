package com.hisu.smart.dj.entity;

import java.io.Serializable;
import java.util.List;

public class NewsInfoResponse implements Serializable {

    /**
     * systemTime : 2019-01-31 06:22:22
     * resultCode : 200
     * data : {"id":13,"content":"<p style=\"text-align: justify;\">美国国务卿蓬佩奥周三（11月28日）表示，基于他看到的所有情报，没有直接的证据表明沙特王储穆罕默德·本·萨勒曼与卡舒吉的谋杀有关。<\/p><p style=\"text-align: justify;\">据路透社报道，美国参议院28日举行闭门会议，听取国务卿蓬佩奥和防长马蒂斯关于美沙关系的汇报，蓬佩奥是在会议结束后接受记者采访时发表上述言论的。<\/p><p style=\"text-align: justify;\">不过，据路透社报道，许多议员在会议结束后却并不这么表示。参议院外交委员会主席、共和党人鲍勃·考克（Bob Corker）在闭门会议后对记者说：\u201c我不认在这个房间里有任何人不认为他（沙特王储）对这件事负有责任。\u201d<\/p><p style=\"text-align: justify;\">在这次闭门会议前，有媒体报道称，作为熟悉卡舒吉案案情的中情局（CIA）局长哈斯佩尔\u201c被缺席\u201d此次会议，引发争议。<\/p><p style=\"text-align: justify;\">路透社报道，中央情报局已经得出结论，是沙特阿拉伯王储穆罕默德·本·萨勒曼下令杀死了卡舒吉。哈斯佩尔曾亲自经前往伊斯坦布尔听取卡舒吉被害录音。<\/p><p style=\"text-align: justify;\">英国《卫报》27日称，美国在有关国家安全的重要会议上，按照惯例，会有情报高官参加，因此哈斯佩尔\u201c被缺席\u201d事件是\u201c前所未有的\u201d。有美国官员表示，不让哈斯佩尔参加会议的决定是白宫做出的。媒体将此次事件解读为，特朗普政府试图让情报机构噤声。<\/p><p style=\"text-align: justify;\">共和党参议员林赛·格雷厄姆（Lindsey Graham）周三表示，在听取中情局对此事的简报之前，他将停止在任何关键问题上的投票，包括政府支出法案。<\/p><p style=\"text-align: justify;\">参议院外交关系委员会的资深成员，新泽西州民主党籍联邦参议员罗伯特·梅南德兹议员告诉记者说：\u201c不让哈斯佩尔出席这次简报会是掩盖真相。这让我明白了关于这里真实情况的许多信息。\u201d<\/p><p style=\"text-align: justify;\">美国之音指出，沙特记者卡舒吉在沙特驻土耳其领事馆遇害近两个月后，美国国务卿蓬佩奥和国防部长马蒂斯敦促维护华盛顿与利雅得长期的密切关系，其中包括美国支持沙特在也门领导的军事行动。蓬佩奥的表态显示，白宫和议员们在关于沙特的问题上的意见分歧加剧。<\/p>","publishTime":"2018-11-26 00:00:00","isNeedSign":false,"name":"蓬佩奥：沙特王储和卡舒吉之死无直接联系","images":[],"url":"","mediaType":1}
     * resultDesc : 操作成功
     * inServer : http://171.8.79.251/
     * outServer : http://171.8.79.251/
     */

    private String systemTime;
    private int resultCode;
    private DataBean data;
    private String resultDesc;
    private String inServer;
    private String outServer;

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getInServer() {
        return inServer;
    }

    public void setInServer(String inServer) {
        this.inServer = inServer;
    }

    public String getOutServer() {
        return outServer;
    }

    public void setOutServer(String outServer) {
        this.outServer = outServer;
    }

    public static class DataBean {
        /**
         * id : 13
         * content : <p style="text-align: justify;">美国国务卿蓬佩奥周三（11月28日）表示，基于他看到的所有情报，没有直接的证据表明沙特王储穆罕默德·本·萨勒曼与卡舒吉的谋杀有关。</p><p style="text-align: justify;">据路透社报道，美国参议院28日举行闭门会议，听取国务卿蓬佩奥和防长马蒂斯关于美沙关系的汇报，蓬佩奥是在会议结束后接受记者采访时发表上述言论的。</p><p style="text-align: justify;">不过，据路透社报道，许多议员在会议结束后却并不这么表示。参议院外交委员会主席、共和党人鲍勃·考克（Bob Corker）在闭门会议后对记者说：“我不认在这个房间里有任何人不认为他（沙特王储）对这件事负有责任。”</p><p style="text-align: justify;">在这次闭门会议前，有媒体报道称，作为熟悉卡舒吉案案情的中情局（CIA）局长哈斯佩尔“被缺席”此次会议，引发争议。</p><p style="text-align: justify;">路透社报道，中央情报局已经得出结论，是沙特阿拉伯王储穆罕默德·本·萨勒曼下令杀死了卡舒吉。哈斯佩尔曾亲自经前往伊斯坦布尔听取卡舒吉被害录音。</p><p style="text-align: justify;">英国《卫报》27日称，美国在有关国家安全的重要会议上，按照惯例，会有情报高官参加，因此哈斯佩尔“被缺席”事件是“前所未有的”。有美国官员表示，不让哈斯佩尔参加会议的决定是白宫做出的。媒体将此次事件解读为，特朗普政府试图让情报机构噤声。</p><p style="text-align: justify;">共和党参议员林赛·格雷厄姆（Lindsey Graham）周三表示，在听取中情局对此事的简报之前，他将停止在任何关键问题上的投票，包括政府支出法案。</p><p style="text-align: justify;">参议院外交关系委员会的资深成员，新泽西州民主党籍联邦参议员罗伯特·梅南德兹议员告诉记者说：“不让哈斯佩尔出席这次简报会是掩盖真相。这让我明白了关于这里真实情况的许多信息。”</p><p style="text-align: justify;">美国之音指出，沙特记者卡舒吉在沙特驻土耳其领事馆遇害近两个月后，美国国务卿蓬佩奥和国防部长马蒂斯敦促维护华盛顿与利雅得长期的密切关系，其中包括美国支持沙特在也门领导的军事行动。蓬佩奥的表态显示，白宫和议员们在关于沙特的问题上的意见分歧加剧。</p>
         * publishTime : 2018-11-26 00:00:00
         * isNeedSign : false
         * name : 蓬佩奥：沙特王储和卡舒吉之死无直接联系
         * images : []
         * url :
         * mediaType : 1
         */

        private int id;
        private String content;
        private String publishTime;
        private boolean isNeedSign;
        private String name;
        private String url;
        private int mediaType;
        private List<String> images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public boolean isIsNeedSign() {
            return isNeedSign;
        }

        public void setIsNeedSign(boolean isNeedSign) {
            this.isNeedSign = isNeedSign;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getMediaType() {
            return mediaType;
        }

        public void setMediaType(int mediaType) {
            this.mediaType = mediaType;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
