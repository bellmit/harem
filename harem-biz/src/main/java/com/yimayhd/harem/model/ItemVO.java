package com.yimayhd.harem.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yimayhd.harem.util.NumUtil;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemFeatureKey;
import com.yimayhd.ic.client.model.enums.ItemPicUrlsKey;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ItemVO extends ItemDO {

    private static final long serialVersionUID = -1394982462767288880L;
    private String itemSkuVOStr;//sku列表 jsonString

    private double priceY;//价格（单位:元）

    private List<ItemSkuVO> itemSkuVOList;//

    private List<ItemSkuVO> itemSkuVOListAll;//所有组合的list

    private List<Integer> skuTdRowNumList;

    private int sort = 1;//商品排序字段(默认为1)

    private Long endBookTimeLimit;//酒店可入住时间限制(存feature中)

    private Integer grade;//评分(存feature中)

    private String smallListPic;//方形小列表图，主要用于订单
    private String bigListPic;//扁长大列表图，主要用于伴手礼等商品列表
    private String coverPics;//封面大图String
    private List<String> picList;//封面大图List

    public static ItemDO getItemDO(ItemVO itemVO)throws Exception{
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemVO, itemDO);
        //元转分
        itemDO.setPrice((long) (itemVO.getPriceY() * 100));

        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListByStr())){
            List<ItemSkuDO> itemSkuDOList = new ArrayList<ItemSkuDO>();
            for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
                if(itemSkuVO.isChecked()) {
                    itemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                }
            }
            itemDO.setItemSkuDOList(itemSkuDOList);
        }
        //提前预定时间(暂时只有酒店用) 此代码有问题，需要删除
        /*if (null != itemVO.getEndBookTimeLimit()) {
            ItemFeature itemFeature = null;
            if (null != itemVO.getItemFeature()) {
                itemFeature = itemDO.getItemFeature();
                itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, itemVO.getEndBookTimeLimit() * 24 * 3600);
            } else {
                itemFeature = new ItemFeature(null);
                itemFeature.put(ItemFeatureKey.END_BOOK_TIME_LIMIT, itemVO.getEndBookTimeLimit() * 24 * 3600);
                itemDO.setItemFeature(itemFeature);
            }
            itemDO.setFeature(JSON.toJSONString(itemFeature));
        }*/
        //picUrls转换， 此代码有问题，需要删除
        /*if(StringUtils.isNotBlank(itemVO.getSmallListPic())){
            itemDO.addPicUrls(ItemPicUrlsKey.BIG_LIST_PIC,itemVO.getSmallListPic());
        }
        if(StringUtils.isNotBlank(itemVO.getBigListPic())){
            itemDO.addPicUrls(ItemPicUrlsKey.SMALL_LIST_PIC,itemVO.getBigListPic());
        }
        if(StringUtils.isNotBlank(itemVO.getCoverPics())){
            itemDO.addPicUrls(ItemPicUrlsKey.COVER_PICS, itemVO.getCoverPics());

        }*/
        //评分（暂时普通商品用）,此代码有问题，需要删除
        /*if(null != itemVO.getGrade()){
            ItemFeature itemFeature = null;
            if (null != itemVO.getItemFeature()) {
                itemFeature = itemDO.getItemFeature();
                itemFeature.put(ItemFeatureKey.GRADE, itemVO.getGrade());
            } else {
                itemFeature = new ItemFeature(null);
                itemFeature.put(ItemFeatureKey.GRADE, itemVO.getGrade());
                itemDO.setItemFeature(itemFeature);
            }
        }*/
        itemDO.setItemProperties(itemVO.getItemProperties());
        return itemDO;
    }
    //不能在getItemDO之后调用（修改用时）
    public static CommonItemPublishDTO getCommonItemPublishDTO(ItemVO itemVO)throws Exception{
        CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
        ItemDO itemDO = ItemVO.getItemDO(itemVO);
        commonItemPublishDTO.setItemDO(itemDO);//更新的时候貌似没有用了
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuVOListByStr())){
            //新增sku数组
            List<ItemSkuDO> addItemSkuDOList = new ArrayList<ItemSkuDO>();
            //删除sku数组
            List<ItemSkuDO> delItemSkuDOList = new ArrayList<ItemSkuDO>();
            //修改sku数组
            List<ItemSkuDO> updItemSkuDOList = new ArrayList<ItemSkuDO>();
            for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
                if(0 == itemSkuVO.getId()){
                    if(itemSkuVO.isChecked()){//没有id，有checked是新增
                        addItemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                    }
                }else{
                    if(!itemSkuVO.isChecked()){//有id，没有checked是删除
                        delItemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                    }else{
                        if(itemSkuVO.isModifyStatus()){//有id，有checked，有modifayStatus是修改
                            updItemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
                        }
                    }
                }
            }
            commonItemPublishDTO.setItemSkuDOList(itemDO.getItemSkuDOList());
            commonItemPublishDTO.setAddItemSkuDOList(addItemSkuDOList);
            commonItemPublishDTO.setDelItemSkuDOList(delItemSkuDOList);
            commonItemPublishDTO.setUpdItemSkuDOList(updItemSkuDOList);
        }
        return commonItemPublishDTO;

    }
    public static ItemVO getItemVO(ItemDO itemDO,CategoryVO categoryVO)throws Exception{
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemDO, itemVO);
        //分转元
        itemVO.setPriceY(NumUtil.moneyTransformDouble(itemVO.getPrice()));
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuDOList())){
            List<ItemSkuVO> itemSkuVOList = new ArrayList<ItemSkuVO>();
            for (ItemSkuDO itemSkuDO : itemVO.getItemSkuDOList()){
                itemSkuVOList.add(ItemSkuVO.getItemSkuVO(itemSkuDO));
            }
            itemVO.setItemSkuVOList(itemSkuVOList);
        }

        if(null != itemVO.getItemFeature()){
            //提前预定时间(暂时酒店用)
            itemVO.setEndBookTimeLimit((long) (itemVO.getItemFeature().getEndBookTimeLimit() / (24 * 3600)));
            //评分（暂时普通商品用）
            itemVO.setGrade(itemVO.getItemFeature().getGrade());
        }
        //picUrls转对应的list
        if(StringUtils.isNotBlank(itemVO.getPicUrlsString())){
            itemVO.setSmallListPic(PicUrlsUtil.getSmallListPic(itemDO));
            itemVO.setBigListPic(PicUrlsUtil.getBigListPic(itemDO));
            itemVO.setPicList(PicUrlsUtil.getPicList(itemDO));
        }
        //个性化处理,构建sku表格所用结构
        List<Set<String>> tranSetList = new ArrayList<Set<String>>();
        //sku属性td的rowspan
        List<Integer> skuTdRowNumList = new ArrayList<Integer>();
        //构建所有的属性组合
        List<ItemSkuVO> itemSkuVOListAll = new ArrayList<ItemSkuVO>();
        //TODO 有自定义属性的时候有问题
        if(CollectionUtils.isNotEmpty(categoryVO.getSellCategoryPropertyVOs())){
            //构建属性列表
            for(CategoryPropertyValueVO categoryPropertyValueVO : categoryVO.getSellCategoryPropertyVOs()){
                tranSetList.add(new HashSet<String>());
                skuTdRowNumList.add(0);
            }
            List<ItemSkuVO> itemSkuVOListTran = itemVO.getItemSkuVOList();
            //填充属性列表
            for (int i = 0; i <itemSkuVOListTran.size(); i++) {
                List<ItemSkuPVPair> itemSkuPVPairListTran = itemSkuVOListTran.get(i).getItemSkuPVPairList();
                for (int j = 0; j < itemSkuPVPairListTran.size(); j++) {
                    //pId + vId + vTxt决定唯一
                    String str = String.valueOf(itemSkuPVPairListTran.get(j).getPId()) + String.valueOf(itemSkuPVPairListTran.get(j).getVId()) + null == itemSkuPVPairListTran.get(j).getVTxt() ? "" : itemSkuPVPairListTran.get(j).getVTxt();
                    tranSetList.get(j).add(str);
                }
            }
            //计算skuTdRowNumList
            int len = tranSetList.size();
            for (int i = 0; i < len; i++) {
                if(i == len - 1){
                    skuTdRowNumList.set(i,1);
                }else if(i == len - 2){
                    skuTdRowNumList.set(i,tranSetList.get(i - 1).size());
                }else{
                    int rowNum = 1;
                    for (int j = i + 1; j < len; j++) {
                        rowNum = rowNum * tranSetList.get(j).size();
                    }
                    skuTdRowNumList.set(i,rowNum);
                }
            }

            //构建所有的属性组合
            itemSkuVOListAll = CategoryVO.getItemSkuVOListAll(categoryVO);
            itemVO.setItemSkuVOListAll(itemSkuVOListAll);
            //根据check状态设置check
            List<ItemSkuVO> itemSkuVOList = itemVO.getItemSkuVOList();
            for (int i = 0; i < itemSkuVOList.size(); i++) {
                for (int j = 0; j < itemSkuVOListAll.size(); j++) {
                    if(isEqual(itemSkuVOList.get(i).getItemSkuPVPairList(),itemSkuVOListAll.get(j).getItemSkuPVPairList())){
                        itemSkuVOListAll.get(j).setChecked(true);
                        itemSkuVOListAll.get(j).setId(itemSkuVOList.get(i).getId());
                        itemSkuVOListAll.get(j).setStockNum(itemSkuVOList.get(i).getStockNum());
                        itemSkuVOListAll.get(j).setPriceY(itemSkuVOList.get(i).getPriceY());
                        itemSkuVOListAll.get(j).setVersion(itemSkuVOList.get(i).getVersion());
                        break;
                    }

                }
                //顺便把category中的check属性设置了（以后去掉参数categoryVO的时候，可以挪出去）
                for (ItemSkuPVPair itemSkuPVPair : itemSkuVOList.get(i).getItemSkuPVPairList()){
                    for (CategoryPropertyValueVO categoryPropertyValueVO : categoryVO.getSellCategoryPropertyVOs()){
                        //pId相同时
                        if(itemSkuPVPair.getPId() == categoryPropertyValueVO.getCategoryPropertyVO().getId()){
                            for (CategoryValueVO categoryValueVO : categoryPropertyValueVO.getCategoryPropertyVO().getCategoryValueVOs()){
                                //vId和vTxt相同时
                                if(itemSkuPVPair.getVId() == categoryValueVO.getId() && itemSkuPVPair.getVTxt().equals(categoryValueVO.getText())){
                                    categoryValueVO.setChecked(true);
                                }
                            }
                        }
                    }
                }
            }
        }
        itemVO.setSkuTdRowNumList(skuTdRowNumList);

        return itemVO;
    }

    //供vm页面用
    public String getItemSkuVOListAllStr(){
        return JSON.toJSONString(this.itemSkuVOListAll, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static boolean isEqual(List<ItemSkuPVPair> list1,List<ItemSkuPVPair> list2){
        if(CollectionUtils.isNotEmpty(list1) && CollectionUtils.isNotEmpty(list2) && list1.size() == list2.size()){
            for (int i = 0; i < list1.size(); i++) {
                if(list1.get(i).getPId() != list2.get(i).getPId() || !list1.get(i).getPTxt().equals(list2.get(i).getPTxt()) || list1.get(i).getVId() != list2.get(i).getVId() || !list1.get(i).getVTxt().equals(list2.get(i).getVTxt())){
                    return false;
                }
            }
            return true;

        }else{
            return false;
        }
    }

    /**
     * sku jsonStr转list对象
     * @return
     */
    public List<ItemSkuVO> getItemSkuVOListByStr(){
        if(StringUtils.isBlank(this.itemSkuVOStr)){
            return null;
        }
        List<ItemSkuVO> itemSkuVOList = JSON.parseArray(this.itemSkuVOStr,ItemSkuVO.class);
        return itemSkuVOList;
    }

    public String getItemSkuVOStr() {
        return itemSkuVOStr;
    }

    public void setItemSkuVOStr(String itemSkuVOStr) {
        this.itemSkuVOStr = itemSkuVOStr;
    }

    public List<ItemSkuVO> getItemSkuVOList() {
        return itemSkuVOList;
    }

    public void setItemSkuVOList(List<ItemSkuVO> itemSkuVOList) {
        this.itemSkuVOList = itemSkuVOList;
    }

    public double getPriceY() {
        return priceY;
    }

    public void setPriceY(double priceY) {
        this.priceY = priceY;
    }

    public List<Integer> getSkuTdRowNumList() {
        return skuTdRowNumList;
    }

    public void setSkuTdRowNumList(List<Integer> skuTdRowNumList) {
        this.skuTdRowNumList = skuTdRowNumList;
    }

    public List<ItemSkuVO> getItemSkuVOListAll() {
        return itemSkuVOListAll;
    }

    public void setItemSkuVOListAll(List<ItemSkuVO> itemSkuVOListAll) {
        this.itemSkuVOListAll = itemSkuVOListAll;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Long getEndBookTimeLimit() {
        return endBookTimeLimit;
    }

    public void setEndBookTimeLimit(Long endBookTimeLimit) {
        this.endBookTimeLimit = endBookTimeLimit;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSmallListPic() {
        return smallListPic;
    }

    public void setSmallListPic(String smallListPic) {
        this.smallListPic = smallListPic;
    }

    public String getBigListPic() {
        return bigListPic;
    }

    public void setBigListPic(String bigListPic) {
        this.bigListPic = bigListPic;
    }

    public String getCoverPics() {
        return coverPics;
    }

    public void setCoverPics(String coverPics) {
        this.coverPics = coverPics;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
