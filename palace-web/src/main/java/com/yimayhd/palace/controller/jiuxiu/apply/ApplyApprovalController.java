package com.yimayhd.palace.controller.jiuxiu.apply;


import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
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
import com.yimayhd.palace.model.CategoryVO;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        MemResult<ExamineInfoDTO> examineInfoDTOResult = examineDealServiceRef.queryMerchantExamineInfoById(approveVO.getId());
        int type = examineInfoDTOResult.getValue().getType();
        // 如果审核通过并且身份是商户,此处不做任何处理,待商品类目分配完成后集中更新申请状态,新增申请明细,新增商户,新增商户与商品类目关系,发送短信
        if (approveVO.isPass() && type == 2) {
            checkResult = new BizResultSupport();
            return checkResult;
        }
        // 驳回,身份为达人进行直接执行1期的审批驳回动作
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
                MerchantScopeDO merchantScopeDOQuery = new MerchantScopeDO();
                merchantScopeDOQuery.setDomainId(Constant.DOMAIN_JIUXIU);
                merchantScopeDOQuery.setSellerId(result.getValue().getSellerId());
                MemResult<List<BusinessScopeDO>> businessScopeResult = applyService.getBusinessScopesBySellerId(merchantScopeDOQuery);
                if (businessScopeResult != null && businessScopeResult.isSuccess() && !businessScopeResult.getValue().isEmpty()) {
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
                MerchantCategoryDO merchantCategoryDOQuery = new MerchantCategoryDO();
                merchantCategoryDOQuery.setDomainId(Constant.DOMAIN_JIUXIU);
                merchantCategoryDOQuery.setId(12l);
                MemResult<List<MerchantCategoryDO>> merchantCategoryResult = applyService.getMerchantCategory(merchantCategoryDOQuery);
                if (null != merchantCategoryResult && merchantCategoryResult.isSuccess() && merchantCategoryResult.getValue() != null && !merchantCategoryResult.getValue().isEmpty()) {
                    MerchantCategoryDO merchantCategoryDO = merchantCategoryResult.getValue().get(0);
                    long parentId = merchantCategoryDO.getParentId();
                    StringBuilder builder = new StringBuilder();
                    List<String> names = new ArrayList<>();
                    while (parentId > 0) {
                        merchantCategoryDOQuery.setId(parentId);
                        MemResult<List<MerchantCategoryDO>> temp = applyService.getMerchantCategory(merchantCategoryDOQuery);
                        if (temp.isSuccess() && temp != null && temp.getValue() != null && !temp.getValue().isEmpty()) {
                            names.add(temp.getValue().get(0).getName());
                            parentId = temp.getValue().get(0).getParentId();
                        }
                    }
                    for (int i = names.size() - 1; i >= 0; i--) {
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

                // 根据sellerId查询商家的商品类目
                MemResult<List<MerchantItemCategoryDO>> merchantItemCategoryResult = merchantItemCategoryService.findMerchantItemCategoriesByExamineId(Constant.DOMAIN_JIUXIU, id);
                Map<String, List<CategoryVO>> itemCategoryMap = new LinkedHashMap<>();
                if (merchantItemCategoryResult != null && merchantItemCategoryResult.isSuccess() && !merchantItemCategoryResult.getValue().isEmpty()) {

                    // 加载商品类目id
                    long[] categoryIds = new long[merchantItemCategoryResult.getValue().size()];
                    List<MerchantItemCategoryDO> merchantItemCategoryDOs = merchantItemCategoryResult.getValue();
                    for (int i = 0; i < merchantItemCategoryDOs.size(); i++) {
                        categoryIds[i] = merchantItemCategoryDOs.get(i).getItemCategoryId();
                    }


//                    for (BusinessScopeDO businessScopeDO : businessScopeResult.getValue()) {
//                        if(!itemCategoryMap.containsKey(businessScopeDO.getName())) {
//                            itemCategoryMap.put(businessScopeDO.getName(), new ArrayList<CategoryVO>());
//                        }
//                    }

                    MemResult<List<ScopeItemCategoryDO>> scopeItemCategoryResult = scopeItemCategoryService.findScopeItemCategoriesByCategory(Constant.DOMAIN_JIUXIU, categoryIds);
                    outer:
                    for (ScopeItemCategoryDO scopeItemCategoryDO : scopeItemCategoryResult.getValue()) {
                        List<CategoryDO> categoryDOs = categoryService.getCategoryDOList(scopeItemCategoryDO.getItemCategoryId());
                        List<CategoryVO> categoryVOs = new ArrayList<>();
                        for (CategoryDO categoryDO : categoryDOs) {
                            CategoryVO vo = new CategoryVO();
                            vo.setName(categoryDO.getName());
                            categoryVOs.add(vo);
                        }
                        for (BusinessScopeDO businessScopeDO : businessScopeResult.getValue()) {
                            if (!itemCategoryMap.containsKey(businessScopeDO.getName())) {
                                itemCategoryMap.put(businessScopeDO.getName(), new ArrayList<CategoryVO>());
                            }
                            if (businessScopeDO.getId() == scopeItemCategoryDO.getBusinessScopeId()) {
                                itemCategoryMap.get(businessScopeDO.getName()).addAll(categoryVOs);
                                continue outer;
                            }
                        }
                    }
                    model.addAttribute("itemCategory", itemCategoryMap);
                }


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
        BusinessScopeDO businessScopeDOQuery = new BusinessScopeDO();
        businessScopeDOQuery.setDomainId(Constant.DOMAIN_JIUXIU);
        MemResult<List<BusinessScopeDO>> businessScopeMemResult = businessScopeService.findBusinessScopesByScope(businessScopeDOQuery, scopeIds);
        long[] businessScopeIds = new long[businessScopeMemResult.getValue().size()];
        Map<String, List<CategoryVO>> result = new LinkedHashMap<>();
        for (int i = 0; i < businessScopeMemResult.getValue().size(); i++) {
            businessScopeIds[i] = businessScopeMemResult.getValue().get(i).getId();
//            if (!result.containsKey(businessScopeMemResult.getValue().get(i).getName())) {
//                result.put(businessScopeMemResult.getValue().get(i).getName(), new ArrayList<CategoryDO>());
//            }
        }
        MemResult<List<ScopeItemCategoryDO>> scopeItemCategoryMemResult = scopeItemCategoryService.findScopeItemCategoriesByMerchantScope(1200, businessScopeIds);
        if (scopeItemCategoryMemResult.getValue().isEmpty()) {
            return "";
        }
        outer:
        for (ScopeItemCategoryDO scopeItemCategoryDO : scopeItemCategoryMemResult.getValue()) {
            List<CategoryDO> categoryDOs = categoryService.getCategoryDOList(scopeItemCategoryDO.getItemCategoryId());
            List<CategoryVO> categoryVOs = new ArrayList<>();
            for (CategoryDO categoryDO : categoryDOs) {
                CategoryVO vo = new CategoryVO();
                vo.setId(categoryDO.getId());
                vo.setName(categoryDO.getName());
                vo.setParentId(categoryDO.getParent().getId());
                vo.setStatus(categoryDO.getStatus());
                categoryVOs.add(vo);
            }
            for (BusinessScopeDO businessScopeDO : businessScopeMemResult.getValue()) {
                if (!result.containsKey(businessScopeDO.getName())) {
                    result.put(businessScopeDO.getName(), new ArrayList<CategoryVO>());
                }
                if (businessScopeDO.getId() == scopeItemCategoryDO.getBusinessScopeId()) {
                    result.get(businessScopeDO.getName()).addAll(categoryVOs);
                    break outer;
                }
            }
        }
        model.addAttribute("examineId", id);
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
        String[] array = allocationVO.getCategoryIds().split(",");
        long[] categoryIds = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            categoryIds[i] = Long.parseLong(array[i]);
        }
        MemResultSupport memResultSupport = merchantItemCategoryService.saveMerchantItemCategories(1200, allocationVO.getExamineId(), categoryIds);
        if (!memResultSupport.isSuccess()) {
            bizResultSupport.setPalaceReturnCode(PalaceReturnCode.MERCHANT_BIND_FAILED);
            return bizResultSupport;
        }

        return new BizResultSupport();

    }
}
