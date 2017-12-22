package com.example.content01;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import db.DBHelper;
import db.SQLiteInfo;

/**
 * Created by Administrator on 2017/12/1.
 */

public class MyProvider extends ContentProvider{
    //声明查询的权限
    private static String authority = "us.mifeng";
    //声明三个变量用来标识用户根据什么去查询
    private static int USER = 0;
    private static int ID = 1;
    private static int NAME = 2;
    //设置匹配对象
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    //添加匹配值
    //静态代码块
    static {
        //select * from user
        matcher.addURI(authority,"user",USER);
        //select * from user where _id = 'id';
        matcher.addURI(authority,"user/#",ID);
        //select * from user where body = 'body';
        matcher.addURI(authority,"user/*",NAME);
    }

    private SQLiteDatabase db;

    //创建provider
    @Override
    public boolean onCreate() {
        DBHelper helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        return false;
    }

    //查询
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        //获取查询前要匹配的值
        int code = matcher.match(uri);
        switch (code){
            case 0:
                cursor = db.query(SQLiteInfo.TABLE,null,null,null,null,null,null);
                break;
            case 1:
                //获取id
                long id = ContentUris.parseId(uri);
                cursor = db.query(SQLiteInfo.TABLE,null,SQLiteInfo._ID+" = ?",new String[]{id+""},null,null,null);
                break;
            case 2:
                String name = uri.getLastPathSegment();
                cursor = db.query(SQLiteInfo.TABLE,null,SQLiteInfo.NAME+" = ?",new String[]{name},null,null,null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    //插入
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long insert = db.insert(SQLiteInfo.TABLE, null, values);
        if (insert>0){
            Uri newUri = ContentUris.withAppendedId(uri,insert);
            return newUri;
        }
        return null;
    }

    //删除
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int type = matcher.match(uri);
        int delete = -1;
        switch (type){
            case 0:
                delete = db.delete(SQLiteInfo.TABLE,null,null);
                break;
            case 1:
                long id = ContentUris.parseId(uri);
                delete = db.delete(SQLiteInfo.TABLE, SQLiteInfo._ID + " = ?", new String[]{id + ""});
                break;
            case 2:
                String name = uri.getLastPathSegment();
                delete = db.delete(SQLiteInfo.TABLE,SQLiteInfo.NAME+" =?",new String[]{name});
                break;
        }
        return delete;
    }

    //更新
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int update = db.update(SQLiteInfo.TABLE, values, selection, selectionArgs);
        return update;
    }
}












/*
* 其实和亲爱的腻在一起的时候我总觉得时间过得特别的快，
* 也许快乐的时光总是这样稍纵即逝，我多么希望在每一个清晨睁开的第一眼眼前就是你可爱迷人的模样，
* 我多么希望在每一个深夜都能够紧紧的把你抱在怀里，然后轻声的对你说“亲爱的！晚安”。
　　曾经我一直在想这个世上会不会有一个令我着魔，让我忘记自己，让我的小心脏蠢蠢欲动的人出现，
那时的我从未想过会有这样一个人出现，那个心高气傲、不可一世的我怎么会为一个人着魔甚至忘记自己。
　　直到遇见了你，沈梦思、沈姑娘、冉、亲爱的！你的出现让我做了太多太多我自己都不敢相信的事情！
所有的原则，所有的个性都被我抛诸脑后，我也开始学习试着去关心一个人、了解一个人、她喜欢什么、
她讨厌什么....
　　我并不是一个做任何事情都会认真的人，我妈时常说我这不好那不好，以后那个女孩子愿意跟你，
而我的表现也经常是她所说的那样，但是！我太了解我自己了，那只是不够认真，我时常会觉得认真的我，
我都会觉得意外。
　　对你！我认真了、用心了、感悟了。
　　冉（亲允许我叫一次我的专属昵称）你会经常问我，我到底爱你什么，喜欢你什么，
我想喜欢一个人亦或者爱一个人总归是有根源的，总归是有理由的，有些人爱一个人需要一辈子或者更久，
但我觉得从那一刻起爱已经燃起。
　　如果非得寻找一个理由:
　　因为爱你，所以爱你
　　应为你可爱的笑容
　　因为你那迷人的眼睛
　　因为抱着你的感觉
　　因为有你的生活才充满快乐
　　因为我要向世界大声讲 我爱你
　　你相信前世今生吗？
　　我想上辈子我一定亏欠你太多，上天让我这辈子来好好疼爱你
　　也许是苍天有意而为之，或许是我的造化，或许还是浪漫的巧合让我在这茫茫人海之中，
芸芸众生之界认识你，放佛一泓清泉在我心里淙淙流淌，我想你就是我生命中的那个人，我会千般喜欢你，
百般呵护你，疼爱你，万般爱抚你，宠爱你，体贴你，因为“曾经沧海难为水，
除却巫山不是云”我从来不曾想过会有谁取代你的位置。
　　我们能有今天，是偶然，也是必然，遇到你是缘分，喜欢你，
是命中注定，爱上你，是我情非得已，想念你，是我逼不得已，我的爱因你而甜蜜。
　　我会珍惜你的一切，不论是你貌若天仙，还是你温顺贤良，不论是你娇艳的容颜，还是你女性的温情，
亦或者你任性小孩子气，或者你冰雪聪明？我只能说今后我会像爱我自己一样爱你，
保留着那颗为你怦然而动的心。
　　我想和你
　　我想和你一起在草原上约会，住蒙古包、吃烤全羊、和羊奶酒，我想和你躺在那无尽的草原上
一起细数那数不尽的繁星，我想和你一起去大草原的湖边，等待鸟飞回来，我想和你一起去草原放羊，
我骑着马，你牵着马赶着羊。
　　我想和你一起去看超恐怖的电影，你尖叫，我抱着你
　　我想和你一起去森林采生，我会采那最高树上的果子给你
　　我想和你一起放烟花，你举着花，我点着焰
　　我想和你一起堆雪人，然后在属于你的雪上写上“小笨蛋”
　　我想和你一起去爬山，我要很惬意的吹着小风等着你，然后递上一瓶你迫不及待要喝的水
　　我想和你一起去看日落，顺便也看了日出，我会和贴心的赶走最闲人的蚊子
　　我想和你一起去吃世上最好吃的冰淇淋，你要芒果味，我要奶味，你会说都是你的不准我吃，
然后你一口我一口
　　我想和你一起给你最最讨厌的家伙的自行车放气，你看我放，你跑，我顶着
　　我想和你一起在厨房里做菜，我下油下菜，你加盐加醋
　　我想和你一起去海边，白天出海捕鱼，然后架起炊具，享受一天的成果，晚上就在海边撑起帐篷，
然后哄你睡觉，和你讲讲情话。
　　我想和你一起......
　　我想和你一起.......
　　一辈子只写一次的情书
　　一封值得珍藏一辈子的情书
* */