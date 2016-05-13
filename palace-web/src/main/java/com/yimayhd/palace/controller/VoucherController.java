package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.jiuxiu.helper.CSVUtils;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.service.VoucherTemplateService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.voucher.client.domain.VoucherDO;
import com.yimayhd.voucher.client.enums.EntityType;
import com.yimayhd.voucher.client.enums.VoucherStatus;
import com.yimayhd.voucher.client.enums.VoucherTemplateStatus;
import com.yimayhd.voucher.client.enums.VoucherType;
import com.yimayhd.voucher.client.param.VoucherDTO;
import com.yimayhd.voucher.client.query.VoucherPageQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.voucher.client.result.VcResultSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券管理
 * @author xzj
 */
@Controller
@RequestMapping("/GF/voucherManage")
public class VoucherController extends BaseController {

    @Autowired
    private VoucherTemplateService voucherTemplateService;

    @Autowired
    private SessionManager sessionManager;
    /**
     * 优惠券列表
     *
     * @return 优惠券列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, VoucherListQuery voucherListQuery) throws Exception {
        PageVO<VoucherTemplateVO> pageVO = voucherTemplateService.getList(voucherListQuery);
        model.addAttribute("voucherListQuery",voucherListQuery);
        model.addAttribute("voucherTemplateList",pageVO.getItemList());
        model.addAttribute("pageVo",pageVO);
        return "/system/voucherTemplate/list";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd() throws Exception {
        return "/system/voucherTemplate/edit";
    }

    /**
     * 根据优惠券ID获取优惠券详情
     *
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String toEdit(Model model, @PathVariable(value = "id") long id,String edit) throws Exception {
        VoucherTemplateVO voucherTemplateVO = voucherTemplateService.getById(id);
        voucherTemplateVO.setRequirement(Math.round(voucherTemplateVO.getRequirement() / 100));
        voucherTemplateVO.setValue(Math.round(voucherTemplateVO.getValue() / 100));
        model.addAttribute("voucherDO",voucherTemplateVO);
        model.addAttribute("edit",edit);
        return "/system/voucherTemplate/edit";
    }

    /**
     * 新增优惠券
     * @return 优惠券详情
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(VoucherTemplateVO voucherTemplateVO) throws Exception {
        UserDO userDO = sessionManager.getUser();
        if (userDO != null){
            voucherTemplateVO.setOperator(userDO.getNickname());
        }
        voucherTemplateVO.setEntityType(EntityType.SHOP.getType());
        voucherTemplateVO.setEntityId(1000);
        voucherTemplateVO.setDomain(1100);
        //新增默认下架状态
        voucherTemplateVO.setRequirement(Math.round(voucherTemplateVO.getRequirement() * 100));
        voucherTemplateVO.setValue(Math.round(voucherTemplateVO.getValue() * 100));
       // voucherTemplateVO.setStatus(VoucherTemplateStatus.INACTIVE.getStatus());
        voucherTemplateVO.setEndTime(getEndTime(voucherTemplateVO.getEndTime()));
        voucherTemplateVO.setVoucherType(VoucherType.SUM_REDUCE.getType());
        voucherTemplateVO.setIssueType(voucherTemplateVO.getIssueType());
        voucherTemplateService.add(voucherTemplateVO);
       
        return "/success";
    }

    @RequestMapping("/setJoinStatus")
    @ResponseBody
    public ResponseVo setJoinStatus(long id, int status){
        try {
            VoucherDTO voucherDTO = new VoucherDTO();
        	voucherDTO.setUserId(sessionManager.getUserId());
        	voucherDTO.setTemplateId(id);
            if (VoucherTemplateStatus.ACTIVE.getStatus() == status){
            	voucherTemplateService.disableVoucher(voucherDTO);
            }
            if (VoucherTemplateStatus.INACTIVE.getStatus() == status || VoucherTemplateStatus.INIT.getStatus() == status){
            	voucherTemplateService.enableVoucher(voucherDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVo.error(e);
        }
        return ResponseVo.success();
    }
    /**
     * 根据优惠券ID修改优惠券
     *
     * @return 优惠券
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable(value = "id") long id,VoucherTemplateVO voucherTemplateVO) throws Exception {
        UserDO userDO = sessionManager.getUser();
        if (userDO != null){
            voucherTemplateVO.setOperator(userDO.getNickname());
        }
        voucherTemplateVO.setId(id);
        voucherTemplateVO.setEndTime(getEndTime(voucherTemplateVO.getEndTime()));
        voucherTemplateVO.setRequirement(Math.round(voucherTemplateVO.getRequirement() * 100));
        voucherTemplateVO.setValue(Math.round(voucherTemplateVO.getValue() * 100));
        voucherTemplateService.modify(voucherTemplateVO);
        return "/success";
    }

    private Date getEndTime(Date endTime) throws ParseException {
        String str = DateUtil.date2StringByDay(endTime);
        str = str + " 23:59:59";
        return DateUtil.convertStringToDate(DateUtil.DATE_TIME_FORMAT,str);
    }
    
    /**
     * 导出优惠劵
     * @param model
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/export/{id}")
	@ResponseBody
	public  String export(Model model,@PathVariable(value = "id") long id) throws Exception {
		List exportData = new ArrayList<Map>();
		Map row = null;
		VoucherPageQuery voucherPageQuery = new VoucherPageQuery();
		voucherPageQuery.setVoucherTemplateId(id);
		Integer pageNo = 1 ;
		voucherPageQuery.setPageSize(100);
		voucherPageQuery.setHasNextMod(true);
		VcBasePageResult<VoucherDO> result = null ; 
		do {
			voucherPageQuery.setPageNo(pageNo);
			result = voucherTemplateService.exportAllVouchers(voucherPageQuery);
			if(result.isSuccess()){
				for(int i=0;i<result.getList().size();i++){
					row = new LinkedHashMap<String, String>();
					row.put("1", result.getList().get(i).getVoucherCode());
					exportData.add(row);
				}
			}
			pageNo++;
		} while (result.isSuccess() && result.isHasNext());
		
		
		
//		Map row1 = new LinkedHashMap<String, String>();
//		row1.put("1", "1111111111");
//		exportData.add(row1);
//		for(int i=0;i<9999;i++){
//			row1 = new LinkedHashMap<String, String>();
//			row1.put("1", "2111111111");
//			exportData.add(row1);
//		}
		LinkedHashMap map = new LinkedHashMap();
		map.put("1", "优惠劵码");

		String sPath = CSVUtils.getContextRealPath();
		//String path = "D:\\export2\\";
		String fileName = "export";
		File file = CSVUtils.createCSVFile(exportData, map, sPath, fileName);
		String fileName2 = file.getName();
		System.out.println("文件名称：" + fileName2);
		
		CSVUtils.exportFile(response, sPath, fileName2);
		
		CSVUtils.deleteFile(sPath, fileName2);
				
		System.out.println("删除文件成功");
		return "";
	}
	
}
