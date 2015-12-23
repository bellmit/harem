package com.yimayhd.harem.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yimayhd.harem.model.enums.ItemSkuStatus;
import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import com.yimayhd.ic.client.model.domain.CategoryValueDO;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.param.item.CommonItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuPVPair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.ognl.CollectionElementsAccessor;
import org.springframework.beans.BeanUtils;

import java.util.*;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ItemVO extends ItemDO {

    private String itemSkuVOStr;//sku列表 jsonString

    private double priceY;//价格（单位:元）

    private List<ItemSkuVO> itemSkuVOList;//

    private List<ItemSkuVO> itemSkuVOListAll;//所有组合的list

    private List<Integer> skuTdRowNumList;

    private List<String> picUrlList;

    public static ItemDO getItemDO(ItemVO itemVO){
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemVO, itemDO);
        //元转分
        itemDO.setPrice((long) (itemVO.getPriceY() * 100));
        List<ItemSkuDO> itemSkuDOList = new ArrayList<ItemSkuDO>();
        for (ItemSkuVO itemSkuVO : itemVO.getItemSkuVOListByStr()){
            if(itemSkuVO.isChecked()) {
                itemSkuDOList.add(ItemSkuVO.getItemSkuDO(itemVO, itemSkuVO));
            }
        }
        itemDO.setItemSkuDOList(itemSkuDOList);
        itemDO.setItemProperties(itemVO.getItemProperties());
        return itemDO;
    }
    //不能在getItemDO之后调用（修改用时）
    public static CommonItemPublishDTO getCommonItemPublishDTO(ItemVO itemVO){
        CommonItemPublishDTO commonItemPublishDTO = new CommonItemPublishDTO();
        ItemDO itemDO = ItemVO.getItemDO(itemVO);
        commonItemPublishDTO.setItemDO(itemDO);
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

        return commonItemPublishDTO;

    }
    public static ItemVO getItemVO(ItemDO itemDO,CategoryVO categoryVO){
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemDO, itemVO);
        //分转元
        itemVO.setPriceY(itemVO.getPrice() / 100);
        if(CollectionUtils.isNotEmpty(itemVO.getItemSkuDOList())){
            List<ItemSkuVO> itemSkuVOList = new ArrayList<ItemSkuVO>();
            for (ItemSkuDO itemSkuDO : itemVO.getItemSkuDOList()){
                itemSkuVOList.add(ItemSkuVO.getItemSkuVO(itemSkuDO));
            }
            itemVO.setItemSkuVOList(itemSkuVOList);
        }
        //picUrls转list
        if(StringUtils.isNotBlank(itemVO.getPicUrls())){
            String[] arr = itemVO.getPicUrls().split("\\|");
            List<String> list = new ArrayList<String>();
            Collections.addAll(list, arr);
            itemVO.setPicUrlList(list);
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
                    skuTdRowNumList.set(i,tranSetList.get(i - 1).size() + tranSetList.get(i - 2).size());
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

    public List<String> getPicUrlList() {
        return picUrlList;
    }

    public void setPicUrlList(List<String> picUrlList) {
        this.picUrlList = picUrlList;
    }
}
