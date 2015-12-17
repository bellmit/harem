package com.yimayhd.harem.controller;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.service.CommScenicService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;

/**
 * 发布景区（商品）
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/comm/scenicManage")
public class CommScenicManageController extends BaseController {
	@Autowired
	private CommScenicService commScenicService;
	@Resource
	protected ComCenterService comCenterServiceRef;

    /**
     * 新增景区
     * @return 景区
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd() throws Exception {
    	
    	//主题
    	BaseResult<List<ComTagDO>> tagResult = comCenterServiceRef.selectTagListByTagType(TagType.LINETAG.name());
    	/*List<IdNamePair> tags = new ArrayList<IdNamePair>();
		if (tagResult != null && tagResult.isSuccess()) {
			List<ComTagDO> values = tagResult.getValue();
			if (CollectionUtils.isNotEmpty(values)) {
				for (ComTagDO comTagDO : values) {
					tags.add(new IdNamePair(comTagDO.getId(), comTagDO.getName()));
				}
			}
		}*/
    	
    	return "/system/comm/scenic/edit";
    }
    
    
    /**
     * 编辑景区（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    String edit(ItemDO itemDo) throws Exception {
    	/*commScenicService.save(itemDo);*/
        return "/success";
    }

    
  
    
    
}
