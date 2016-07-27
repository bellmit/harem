package com.yimayhd.palace.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.activitycenter.domain.ActActivityPromotionDO;
import com.yimayhd.activitycenter.enums.PromotionStatus;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.helper.PromotionHelper;
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.palace.model.query.ActPromotionPageQuery;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;

/**
 * 单品优惠管理
 * @author czf
 */
@Controller
@RequestMapping("/GF/promotionCommManage")
public class PromotionCommController extends BaseController {

    @Autowired
    private PromotionCommService promotionCommService;
    /**
     * 单品优惠列表
     *
     * @return 单品优惠列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model, ActPromotionPageQuery actPromotionPageQuery) throws Exception {
        actPromotionPageQuery.setLotteryType(EntityType.ITEM.getType());
        PageVO<ActActivityPromotionDO> pageVO = promotionCommService.getList(actPromotionPageQuery);
        List<PromotionType> promotionTypeList = PromotionHelper.getAvaiableItemPromotionTypes();
        List<PromotionStatus> promotionStatusList = Arrays.asList(PromotionStatus.values());
        model.addAttribute("promotionListQuery",actPromotionPageQuery);
        model.addAttribute("pageVo",pageVO);
        model.addAttribute("promotionTypeList",promotionTypeList);
        model.addAttribute("promotionStatusList",promotionStatusList);
        return "/system/promotion/comm/list";
    }

    /**
     * 新增单品优惠
     * @return 单品优惠详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(Model model,int promotionType) throws Exception {
    	PromotionType type = PromotionType.getByType(promotionType);
    	if( type == null ){
    		//FIXME wuzhengfei
    		throw new Exception("无法识别的促销类型") ;
//    		return null;
    	}
        model.addAttribute("promotionType",type.getType());
        model.addAttribute("promotionName",type.name());
        model.addAttribute("entityType",EntityType.ITEM.getType());
        return "/system/promotion/comm/edit";
    }

    /**
     * 根据优惠ID获取单品优惠详情
     *
     * @return 单品优惠详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
        ActActivityEditVO actActivityEditVO = promotionCommService.getById(id);
        //TODO
        int promotionType = 6;
        PromotionType promotionType2 = PromotionType.getByType(promotionType);
        model.addAttribute("actActivityEditVO",actActivityEditVO);
        model.addAttribute("promotionType",promotionType);
        if( promotionType2 != null ){
        	model.addAttribute("promotionName", promotionType2.name());
        }
        model.addAttribute("entityType",EntityType.ITEM.getType());
        return "/system/promotion/comm/edit";
    }

    @RequestMapping(value = "/checkActivityName", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo checkActivityName(String name, int type, Long activityId) throws Exception {
    	long id = activityId == null ? 0 : activityId.longValue() ;
        boolean repeat = promotionCommService.isActivityNameRepeat(name, type, id);
        if ( repeat ){
            return new ResponseVo(ResponseStatus.ERROR);
        }
        return ResponseVo.success();
    }

    /**
     * 新增优惠
     * @return 单品优惠详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(ActActivityEditVO actActivityEditVO) throws Exception {
        //promotionVO.setEntityType(EntityType.SHOP.getType());
        promotionCommService.add(actActivityEditVO);
        return "/success";
    }
    
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public BizResultSupport promotionCheck(ActActivityEditVO actActivityEditVO) throws Exception {
    	//promotionVO.setEntityType(EntityType.SHOP.getType());
    	BizResultSupport result = promotionCommService.check(actActivityEditVO);
    	return result;
    }
    
    

    /**
     * 根据优惠ID修改优惠
     *
     * @return 优惠
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable(value = "id") long id,ActActivityEditVO actActivityEditVO) throws Exception {
//        promotionVO.setId(id);
        actActivityEditVO.getActActivityVO().setId(id);
        promotionCommService.modify(actActivityEditVO);
        return "/success";
    }

    /**
     * 商品上架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo publish(@PathVariable("id") long id) throws Exception {
        if (promotionCommService.publish(id)){
            return new ResponseVo();
        }else {
            return new ResponseVo(ResponseStatus.ERROR);
        }
    }

    /**
     * 商品下架
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo close(@PathVariable("id") long id) throws Exception {
        if (promotionCommService.close(id)){
            return new ResponseVo();
        }else {
            return new ResponseVo(ResponseStatus.ERROR);
        }
    }
}
