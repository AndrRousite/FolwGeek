package com.cn.goldenjobs.config;

/**
 * 配置文件
 * Created by liu-feng on 2016/7/8.
 * 邮箱:w710989327@foxmail.com
 */
public class APIConfig {

    public static final String HOST_NAME = "www.oschina.net";  // 注，空的字符串需要单独填写
    public static final String CLIENT_IDENTIFICATION = "WhenYouSawIt,Well!Bingo!!";
    public static final String COMPANY_IDENTIFICATION = "OSChina.NET";
    public static final String BASE_URL = "http://www.oschina.net/";

    // 好友列表信息服务器地址
    //
    private static final String API_SERVER_TEST = "https://apptest.netease.im/api/"; // 测试
    private static final String API_SERVER = "https://app.netease.im/api/"; // 线上

    public static final String apiServer() {
        return ServerConfig.testServer() ? API_SERVER_TEST : API_SERVER;
    }

    public static final String chatRoomAPIServer() {
        return apiServer() + "chatroom/";
    }

}


final class ServerConfig {

    public enum ServerEnv {
        TEST("t"),
        PRE_REL("p"),
        REL("r"),

        ;
        String tag;

        ServerEnv(String tag) {
            this.tag = tag;
        }
    }

    public static boolean testServer() {
        return ServerEnvs.SERVER == ServerEnv.TEST;
    }
}

final class ServerEnvs {

    //
    // ENVs
    // DEFAULT Env.REL
    //

    static final ServerConfig.ServerEnv SERVER = ServerConfig.ServerEnv.REL;

}