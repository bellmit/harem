package com.yimayhd.palace.controller.jiuxiu.apply;


import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.ScopeItemCategoryDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.membercenter.client.service.BusinessScopeService;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.membercenter.client.service.ScopeItemCategoryService;
import com.yimayhd.membercenter.client.service.examine.ApplyService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.biz.ApplyBiz;
import com.yimayhd.palace.checker.apply.AllocationChecker;
import com.yimayhd.palace.checker.apply.ApplyApproveChecker;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.controller.vo.BusinessScopeVO;
import com.yimayhd.palace.controller.vo.MerchantCategoryVO;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.query.apply.ApplyQuery;
import com.yimayhd.palace.model.vo.apply.AllocationVO;
import com.yimayhd.palace.model.vo.apply.ApplyVO;
import com.yimayhd.palace.model.vo.apply.ApproveVO;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.palace.service.CategoryService;
import com.yimayhd.user.session.manager.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/jiuxiu/apply")
public class ApplyApprovalController extends BaseController {
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private ApplyBiz applyBiz;
    @Autowired
    private ExamineDealService examineDealServiceRef;
    @Autowired
    private ScopeItemCategoryService scopeItemCategoryService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BusinessScopeService businessScopeService;
    @Autowired
    private MerchantItemCategoryService merchantItemCategoryService;
    @Autowired
    private ApplyService applyService;

    @RequestMapping(value = "/list")
    public String applyApproval(Model model) {
        ExamineType[] types = ExamineType.values();
        ExamineStatus[] statuses = ExamineStatus.values();
        model.addAttribute("types", types);
        model.addAttribute("statuses", statuses);
        return "/system/apply/list";
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public BizPageResult<ApplyVO> queryApplies(ApplyQuery applyQuery) {
        BizPageResult<ApplyVO> result = applyBiz.queryApplys(applyQuery);
        return result;
    }

    @RequestMapping(value = "/approve")
    @ResponseBody
    public BizResultSupport approve(ApproveVO approveVO) {
        BizResultSupport checkResult = ApplyApproveChecker.checkApproveVO(approveVO);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        // 如果审核通过,此处不做任何处理,将reason返回页面存入hidden控件,待商品类目分配完成后集中更新申请状态,新增申请明细,新增商户,新增商户与商品类目关系,发送短信
        if (approveVO.isPass()) {
            checkResult = new BizResultSupport();
            checkResult.setCode(Constant.ROUTE_ALLOCATIE_FORM);
            checkResult.setMsg(approveVO.getReason());
            return checkResult;
        }
        long userId = sessionManager.getUserId();
        BizResultSupport result = applyBiz.approve(approveVO, userId);
        return result;
    }

    @RequestMapping(value = "detail")
    public String getMerchantDetail(Model model, long id) {
        try {
            MemResult<ExamineInfoDTO> result = examineDealServiceRef.queryMerchantExamineInfoById(id);
            if (result.isSuccess() && null != result.getValue()) {
                model.addAttribute("examineInfo", result.getValue());
                MemResult<List<MerchantScopeDO>> scopeResult = applyService.getMerchantScopeBySellerId(result.getValue().getSellerId(), Constant.DOMAIN_JIUXIU);
                if (scopeResult.isSuccess() && null != scopeResult.getValue() && !scopeResult.getValue().isEmpty()) {
                    List<MerchantScopeDO> merchantScopeDOs = scopeResult.getValue();
                    List<Long> ids = new ArrayList<>();
                    for (MerchantScopeDO merchantScopeDO : merchantScopeDOs) {
                        ids.add(merchantScopeDO.getBusinessScopeId());
                    }
                    MemResult<List<BusinessScopeDO>> businessScopeResult = applyService.getBusinessScopesByIds(Constant.DOMAIN_JIUXIU, ids);
                    List<BusinessScopeVO> businessScopeVOs = new ArrayList<>();
                    for (BusinessScopeDO businessScopeDO : businessScopeResult.getValue()) {
                        BusinessScopeVO vo = new BusinessScopeVO();
                        vo.setId(businessScopeDO.getId());
                        vo.setName(businessScopeDO.getName());
                        vo.setParentId(businessScopeDO.getParentId());
                        vo.setStatus(businessScopeDO.getStatus());
                        businessScopeVOs.add(vo);
                    }
                    model.addAttribute("scope", businessScopeVOs);
                }
//                MemResult<MerchantCategoryDO> merchantCategoryResult = applyService.getMerchantCategory(Constant.DOMAIN_JIUXIU, result.getValue().getMerchantCategoryId());
                MemResult<MerchantCategoryDO> merchantCategoryResult = applyService.getMerchantCategory(Constant.DOMAIN_JIUXIU, 12l);
                if (null != merchantCategoryResult && merchantCategoryResult.isSuccess() && merchantCategoryResult.getValue() != null) {
                    MerchantCategoryDO merchantCategoryDO = merchantCategoryResult.getValue();
                    long parentId = merchantCategoryDO.getParentId();
                    StringBuilder builder = new StringBuilder();
                    List<String> names = new ArrayList<>();
                    while (parentId > 0) {
                        MemResult<MerchantCategoryDO> temp = applyService.getMerchantCategory(Constant.DOMAIN_JIUXIU, parentId);
                        if (!temp.isSuccess() && null == temp.getValue()) {
                            break;
                        }
                        names.add(temp.getValue().getName());
                        parentId = temp.getValue().getParentId();
                    }
                    for(int i = names.size() - 1;i >= 0;i--) {
                        builder.append(names.get(i)).append(" ");
                    }
                    builder.append(merchantCategoryDO.getName());
                    MerchantCategoryVO vo = new MerchantCategoryVO();
                    vo.setId(merchantCategoryDO.getId());
                    vo.setParentId(merchantCategoryDO.getParentId());
                    vo.setName(builder.toString());
                    vo.setPic(merchantCategoryDO.getPic());
                    vo.setType(merchantCategoryDO.getType());
                    vo.setStatus(merchantCategoryDO.getStatus());
                    model.addAttribute("category", vo);
                }
                model.addAttribute("feature", "直营店");
                model.addAttribute("type", result.getValue().getType());
                model.addAttribute("status", result.getValue().getExaminStatus());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "/system/apply/detail";
    }

    /**
     * 跳转分配产品类目页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/allocation")
    public String allocation(long id, Model model) {
        List<Long> scopeIds = new ArrayList<>();
        scopeIds.add(1L);
        // 找到商户的经营范围
        MemResult<List<BusinessScopeDO>> businessScopeMemResult = businessScopeService.findBusinessScopesByScope(Constant.DOMAIN_JIUXIU, scopeIds);
        long[] businessScopeIds = new long[businessScopeMemResult.getValue().size()];
        Map<String, List<CategoryDO>> result = new LinkedHashMap<>();
        for (int i = 0; i < businessScopeMemResult.getValue().size(); i++) {
            businessScopeIds[i] = businessScopeMemResult.getValue().get(i).getId();
            if (!result.containsKey(businessScopeMemResult.getValue().get(i).getName())) {
                result.put(businessScopeMemResult.getValue().get(i).getName(), new ArrayList<CategoryDO>());
            }
        }
        MemResult<List<ScopeItemCategoryDO>> scopeItemCategoryMemResult = scopeItemCategoryService.findScopeItemCategoriesByMerchantScope(1200, businessScopeIds);
        if (scopeItemCategoryMemResult.getValue().isEmpty()) {
            return "";
        }
        outer:
        for (ScopeItemCategoryDO scopeItemCategoryDO : scopeItemCategoryMemResult.getValue()) {
            List<CategoryDO> categoryDOs = categoryService.getCategoryDOList(scopeItemCategoryDO.getItemCategoryId());
            for (BusinessScopeDO businessScopeDO : businessScopeMemResult.getValue()) {
                if (businessScopeDO.getId() == scopeItemCategoryDO.getBusinessScopeId()) {
                    result.get(businessScopeDO.getName()).addAll(categoryDOs);
                    break outer;
                }
            }
        }
        model.addAttribute("scopeCategories", result);
        return "/system/apply/allocation";
    }

    /**
     * 更新审批状态,新增审批明细,新增商户,绑定商户与商品类目关系,发送短信通知
     *
     * @param allocationVO
     * @return
     */
    @RequestMapping(value = "editAllocation")
    public
    @ResponseBody
    BizResultSupport editAllocation(AllocationVO allocationVO) {
        BizResultSupport bizResultSupport = AllocationChecker.checkAllocationVO(allocationVO);
        if (!bizResultSupport.isSuccess()) {
            return bizResultSupport;
        }
        MemResultSupport memResultSupport = merchantItemCategoryService.saveMerchantItemCategoriesByMerchant(1200, allocationVO.getExamineId(), allocationVO.getCategoryIds());
        if (!memResultSupport.isSuccess()) {
            bizResultSupport.setPalaceReturnCode(PalaceReturnCode.MERCHANT_BIND_FAILED);
            return bizResultSupport;
        }

        return new BizResultSupport();

    }
}
