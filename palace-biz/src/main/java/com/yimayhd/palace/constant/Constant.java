package com.yimayhd.palace.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author wzf
 *
 */
public class Constant {
	
	public static final String ENV_PROD = "PROD";
	
	public static final String TEST = "TEST";
	
	public static int DOMAIN_JIUXIU = 1200 ;
	
	public static String APP = "palace";

	//pageQuery对象字段
	public static int PAGEQUERY_STATUS = 1 ;
	public static int PAGE_SIZE = 20 ;
	public static final int DEFAULT_PAGE_SIZE = 30 ;
	public static final int DEFAULT_PAGE_MAX_SIZE = 100 ;
	public static final int DEFAULT_PAGE_SIZE_TEN = 10 ;
	public static final int DEFAULT_PAGE_NO = 1 ;

	public static final int SHOWCASE_SHOE_TYPE_KEYWORD = 1;//关键字
	public static final int SHOWCASE_SHOE_TYPE_CHOOSE_DESTINATION = 2;//目的地
	public static final int SHOWCASE_ITEM_DETAIL = 3;//详情
	public static final int SHOWCASE_SHOE_TYPE_THEME = 4;//主题
	public static final int SHOWCASE_ITEM_LIST = 5;//商品列表
	public static final int SHOWCASE_SHOE_TYPE_MASTER = 6;//达人
	public static final int SHOWCASE_SHOE_TYPE_FOOD_DETAIL = 7;//美食
	/*public static final int SHOWCASE_TALENT_SERVICE = 8;//达人专题*/
	public static final int SHOWCASE_HOTEL_LIST = 9;//酒店列表
	public static final int SHOWCASE_SCENIC_LIST = 10;//景区列表
	public static final int SHOWCASE_DEFAULT_VALUE = 11;//默认值
	public static final int SHOWCASE_MASTER_CIRCLE_DETAIL = 12;//达人圈详情
	public static final int SHOWCASE_VIEW_TOPIC_DETAIL = 13;//话题详情
	public static final int SHOWCASE_VIEW_TOPIC_LIST = 14;//话题列表
	public static final int SHOWCASE_NEST_BOOTH_LIST = 15;//booth列表（嵌套booth）
	public static final int SHOWCASE_TRAVEL_INFORMATION_LIST = 16;//旅行资讯;达人故事
	public static final int SHOWCASE_CATEGORY_LIST = 17;//品类列表


	public static final String DOT = ".";
	public static final String COMMA = ",";


	public static final int ROUTE_ALLOCATIE_FORM = 0;
	
	public static final String UN_REPEAT_SUBMIT = "请不要重复提交";
	public static final String ILLEGAL_PARAMETER = "非法参数";

	public static final int ERROR_STATUS = - 200 ;
	public static final String AFTERSALE_PIC_MAX_ERR = "图片张数（1-5张）超过限制";
	public static final String AFTERSALE_PIC_POSTFIX_ERR = "仅支持jpg，png，jpeg格式图片";
	public static final String[] AFTERSALE_PIC_POSTFIX = new String[]{"jpg","jpeg","png","JPG","JPEG","PNG"};

	public static final String GF_ORDER_CLOSE = "CLOSE" ;

	public static final int showTypeRadio = 1;//1单选框
	public static final int showTypeCheckBox = 2;//2复选框
	public static final int showTypeSelect = 3;//3下拉框
	public static final int showTypeText = 4;//4文本
	public static final int showTypeLargeText = 5;//5大文本
	
	public static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
	
	public static final String UN_COMPLETE_DATA = "资料不完整，不能启用";
	
	public static final String TOPIC_PREFIX_SUFFIX = "#";
	public static final String TOPIC_REPEAT = "该话题已存在，不需要重复添加";
	public static final String TOPIC_SUG_REPEAT = "该话题已推荐，不需要重复推荐";

	public static final List<String> BOOTH_NAME_FORBID = Arrays.asList("|","*","$","#"," ");

	public static final int EXPORTPAGESIZE = 100;
	public static final int EXPORTMAXCOUNT = 3000;
	//public static final int EXPORTMAXCOUNT = 2;
	public static final String BOOTH_PREFIX_SUFFIX = ",";
	public static final String BOOTH_PREFIX_POSTFIX = "|";

}
