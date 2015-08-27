package com.panda.pweibo.constants;

/**
 * URI地址
 *
 * Created by Administrator on 2015/8/22:14:01.
 */
public interface Uri {

    /** uri服务器 */
    String BASE_URI = "https://api.weibo.com/2/";

    /** 授权回收接口，帮助开发者主动取消用户的授权 */
    String OAUTH2_REVOKE_OAUTH2 = "https://api.weibo.com/oauth2/revokeoauth2";

    /** 获取当前登录用户及其所关注用户的最新微博  */
    String STATUS_HOME_TIMELINE = BASE_URI + "statuses/home_timeline.json";

    /** 根据微博ID返回某条微博的评论列表  */
    String COMMENTS_SHOW = BASE_URI + "comments/show.json";

    /** 对一条微博进行评论  */
    String COMMENTS_CREATE = BASE_URI + "comments/create.json";

    /** 获取当前登录用户的最新评论包括接收到的与发出的 */
    String COMMENTS_TO_ME = BASE_URI + "comments/to_me.json";
}
