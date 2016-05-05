package com.yimayhd.palace.constant;

/**
 * 
 * @author wzf
 *
 */
public class Constant {
	
	public static final String ENV_PROD = "PROD";
	
	public static final String TEST = "TEST";
	
	public static int DOMAIN_JIUXIU = 1200 ;

	//pageQuery对象字段
	public static int PAGEQUERY_STATUS = 1 ;
	public static int PAGE_SIZE = 20 ;
	public static final int DEFAULT_PAGE_SIZE = 30 ;
	public static final int DEFAULT_PAGE_MAX_SIZE = 100 ;
	public static final int DEFAULT_PAGE_SIZE_TEN = 10 ;
	public static final int DEFAULT_PAGE_NO = 1 ;

	public static final int SHOWCASE_SHOE_TYPE_KEYWORD = 1;//关键字
	public static final int SHOWCASE_SHOE_TYPE_CHOOSE_DESTINATION = 2;//目的地
	public static final int SHOWCASE_SHOE_TYPE_ITEM_DETAIL = 3;//详情
	public static final int SHOWCASE_SHOE_TYPE_THEME = 4;//主题
	public static final int SHOWCASE_SHOE_TYPE_ITEM_LIST = 5;//商品列表
	
	public static final String DOT = ".";
	public static final String COMMA = ",";

	public static final int ERROR_STATUS = - 200 ;
	public static final String AFTERSALE_PIC_MAX_ERR = "图片张数（1-5张）超过限制";
	public static final String AFTERSALE_PIC_POSTFIX_ERR = "仅支持jpg，png，jpeg格式图片";
	public static final String[] AFTERSALE_PIC_POSTFIX = new String[]{"jpg","jpeg","png","JPG","JPEG","PNG"};

}
