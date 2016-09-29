package com.example.dllo.thirtysixkr.tools.url;

public final class Kr36Url {
    public static final String URL_HOST = "https://rong.36kr.com/api/mobi/news?pageSize=";

    public static final String URL_COLUMNID = "&columnId=";

    public static final String URL_FINAL = "&pagingAction=up";

    public static final String URL_HOST_INVEST = "https://rong.36kr.com/api/mobi/cf/actions/list?page=";

    public static final String URL_TYPE_INVEST = "&type=";

    public static final String URL_PAGE_SIZE_INVEST = "&pageSize=20";

    public static final String INVESTMENT_ALL = "all";

    public static final String INVESTMENT_UNDERWAY = "underway";

    public static final String INVESTMENT_RAISE = "raise";

    public static final String INVESTMENT_FINISH = "finish";

    public static final String NEW_ALL = "all";

    public static final String NEW_EARLY = "67";

    public static final String NEW_DEEP = "70";

    public static final String NEW_BIG_COMPANY = "23";

    public static final String NEW_EIGHT = "110";

    public static final String NEW_ICON = "69";

    public static final String NEW_FRIEND = "103";

    public static final String NEW_RESEARCH = "71";

    public static final String NEW_TV = "tv";

    public final static String newRotate = "https://rong.36kr.com/api/mobi/roundpics/v4";

    public final static String findRotate = "https://rong.36kr.com/api/mobi/roundpics/v4";


    public final static String news(String page, String part) {
        String str = URL_HOST + page + URL_COLUMNID + part + URL_FINAL;
        return str;
    }

    public final static String investment(int page, String part) {
        String str = URL_HOST_INVEST + page + URL_TYPE_INVEST + part + URL_PAGE_SIZE_INVEST;
        return str;
    }


    public final static String findAll = "https://rong.36kr.com/api/mobi/activity/list?page=1Demo Day: https://rong.36kr.com/api/mobi/activity/list?page=1&categoryId=12+URL_PAGE_SIZE_INVEST0";

    //    final static String findKrZone = "";
//    final static String findInvestment = "";
//    final static String findService = "";
//    final static String findFast = "";
//    final static String findFind = "";
    public final static String research(String str) {
        String url = "https://rong.36kr.com/api/mobi/news/search?keyword=" + str + "&page=1&pageSize=20";
        return url;
    }

    public final static String detailWeb(String str) {
        String url = "https://rong.36kr.com/api/mobi/news/" + str;
        return url;
    }
    public final static String authorDetails(String str){
        String url = "https://rong.36kr.com/api/mobi/news/" + str+"/author-region";
        return url;
    }

}
