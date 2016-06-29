package com.yimayhd.palace.controller;

import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.voucher.client.result.VcBaseResult;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;
import com.yimayhd.palace.service.VoucherTemplateService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import com.yimayhd.voucher.client.domain.VoucherDO;
import com.yimayhd.voucher.client.enums.IssueType;
import com.yimayhd.voucher.client.enums.EntityType;
import com.yimayhd.voucher.client.enums.VoucherTemplateStatus;
import com.yimayhd.voucher.client.enums.VoucherType;
import com.yimayhd.voucher.client.query.VoucherPageQuery;
import com.yimayhd.voucher.client.result.VcBasePageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.*;


/**
 * Created by xinzhanguo on 6/16/16.
 */
@Controller
@RequestMapping("/GF/fcodeManage")
public class FCodeController extends BaseController {

    @Autowired
    private VoucherTemplateService voucherTemplateService;

    @Autowired
    private ItemPublishService itemPublishService;

    @Autowired
    private SessionManager sessionManager;

    /**
     * F码活动列表
     *
     * @return F码活动列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, VoucherListQuery voucherListQuery) throws Exception {

        voucherListQuery.setVoucherType(VoucherType.RAND_CODE.getType());
        PageVO<VoucherTemplateVO> pageVO = voucherTemplateService
                .getList(voucherListQuery);

        model.addAttribute("fCodeListQuery", voucherListQuery);
        model.addAttribute("fCodeTemplateList", pageVO.getItemList());
        model.addAttribute("pageVo", pageVO);
        return "/system/fcode/list";
    }

    /**
     * 新增F码活动
     *
     * @return 新增F码活动
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd() throws Exception {
        return "/system/fcode/edit";
    }
    /**
     * 新增F码活动
     *
     * @return 新增F码活动
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(VoucherTemplateVO voucherTemplateVO, Model model) throws Exception {
        UserDO userDO = sessionManager.getUser();
        if (userDO != null) {
            voucherTemplateVO.setOperator(userDO.getNickname());
            // 关联创建人iD
            voucherTemplateVO.setUserId(userDO.getId());
        }

        // 随机F码
        voucherTemplateVO.setVoucherType(VoucherType.RAND_CODE.getType());
        //关联商品
        voucherTemplateVO.setEntityType(EntityType.ITEM.getType());

        long itemId = voucherTemplateVO.getEntityId();
        if(itemId != 0) {
            ItemPubResult result = itemPublishService.changeToFItem(itemId);
            if(!result.isSuccess()){
                model.addAttribute("message", "商品编码不存在,请确认后提交");
                return "/error";
            }
        } else {
            model.addAttribute("message", "商品ID不正确,请确认后提交");
            return "/error";
        }

        // 生成方式 预先生成
        voucherTemplateVO.setIssueType(IssueType.PRE_GENERATE_WITH_CODE.getType());

        voucherTemplateVO.setDomain(1100);

        // 新增默认下架状态
        voucherTemplateVO.setRequirement(Math.round(voucherTemplateVO
                .getRequirement_() * 100));
        voucherTemplateVO.setValue(Math.round(voucherTemplateVO.getValue_() * 100));

        voucherTemplateVO.setStartTime(new Date());
        voucherTemplateVO.setEndTime(getEndTime());

        VcBaseResult<Long> vcBaseResult = voucherTemplateService.add(voucherTemplateVO);
        if(!vcBaseResult.isSuccess()) {
            return "/error";
        }

        return "/success";
    }
    private Date getEndTime() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date oneyearafter = calendar.getTime();
        return oneyearafter;
    }
    /**
     * 导出F码
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/export/{id}")
    @ResponseBody
    public String export(Model model, @PathVariable(value = "id") long id)
            throws Exception {
        try {
            String fileName = "export.csv";
            StringBuilder sb = new StringBuilder();

            VoucherPageQuery voucherPageQuery = new VoucherPageQuery();
            voucherPageQuery.setVoucherTemplateId(id);
            Integer pageNo = 1;
            voucherPageQuery.setPageSize(100);
            voucherPageQuery.setHasNextMod(true);
            VcBasePageResult<VoucherDO> result = null;
            do {
                voucherPageQuery.setPageNo(pageNo);
                result = voucherTemplateService.exportAllVouchers(voucherPageQuery);
                if (result.isSuccess()) {
                    for (int i = 0; i < result.getList().size(); i++) {
                        sb.append(result.getList().get(i).getVoucherCode() + "\r\n");
                    }
                }
                pageNo++;
            } while (result.isSuccess() && result.isHasNext());

            String str = sb.toString();

            // 以流的形式下载文件。
            byte[] buffer = str.getBytes();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes()));
            // response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }

        return "";
    }
}
