package com.yimayhd.palace.controller.jiuxiu.apply;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.BusinessScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantItemCategoryDO;
import com.yimayhd.membercenter.client.domain.merchant.MerchantQualificationDO;
import com.yimayhd.membercenter.client.domain.merchant.QualificationDO;
import com.yimayhd.membercenter.client.domain.merchant.ScopeItemCategoryDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.BusinessScopeQueryDTO;
import com.yimayhd.membercenter.client.query.MerchantCategoryQueryDTO;
import com.yimayhd.membercenter.client.query.QualificationQueryDTO;
import com.yimayhd.membercenter.client.result.MemResult;
import com.yimayhd.membercenter.client.result.MemResultSupport;
import com.yimayhd.membercenter.client.service.BusinessScopeService;
import com.yimayhd.membercenter.client.service.MerchantItemCategoryService;
import com.yimayhd.membercenter.client.service.QualificationService;
import com.yimayhd.membercenter.client.service.ScopeItemCategoryService;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.membercenter.client.service.examine.MerchantApplyService;
import com.yimayhd.membercenter.enums.ExamineCharacter;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.MerchantType;
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
    private MerchantApplyService merchantApplyService;
    @Autowired
    private QualificationService qualificationService;

    @RequestMapping(value = "/list")
    public String applyApproval(Model model) {
        MerchantType[] types = MerchantType.values();
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

                // 查询资质,展示资质图片
                QualificationQueryDTO qualificationQueryDTO = new QualificationQueryDTO();
                qualificationQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
                qualificationQueryDTO.setSellerId(result.getValue().getSellerId());
                MemResult<List<MerchantQualificationDO>> merchantQualificationResult = qualificationService.getMerchantQualification(qualificationQueryDTO);
                if(merchantQualificationResult != null && merchantQualificationResult.isSuccess() && merchantQualificationResult.getValue() != null && !merchantQualificationResult.getValue().isEmpty()) {
                    List<Long> ids = new ArrayList<>();
                    for(MerchantQualificationDO merchantQualificationDO : merchantQualificationResult.getValue()) {
                        ids.add(merchantQualificationDO.getQulificationId());
                    }
                    qualificationQueryDTO = new QualificationQueryDTO();
                    qualificationQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
                    qualificationQueryDTO.getIdSet().addAll(ids);
                    MemResult<List<QualificationDO>> qualificationResult = qualificationService.getQualification(qualificationQueryDTO);
                    if(qualificationResult != null && qualificationResult.isSuccess() && qualificationResult.getValue() != null && !qualificationResult.getValue().isEmpty()) {
                        List<Map<String,String>> qualificationPictures = new ArrayList<>();
                        outer : for(QualificationDO qualificationDO : qualificationResult.getValue()) {
                            for(MerchantQualificationDO merchantQualificationDO : merchantQualificationResult.getValue()) {
                                if(qualificationDO.getId() == merchantQualificationDO.getQulificationId()) {
                                    Map<String,String> map = new HashMap<>();
                                    map.put("url", merchantQualificationDO.getContent());
                                    map.put("title", qualificationDO.getTitle());
                                    qualificationPictures.add(map);
                                    continue outer;
                                }
                            }

                        }
                        model.addAttribute("qualificationPictures", qualificationPictures);

                    }
                    result.getValue().setMerchantQualifications(merchantQualificationResult.getValue());
                }
                BusinessScopeQueryDTO businessScopeQueryDTOQUery = new BusinessScopeQueryDTO();
                businessScopeQueryDTOQUery.setDomainId(Constant.DOMAIN_JIUXIU);
                businessScopeQueryDTOQUery.setSellerId(result.getValue().getSellerId());
                MemResult<List<MerchantScopeDO>> merchantScopeResult = businessScopeService.getMerchantScope(businessScopeQueryDTOQUery);
                if (merchantScopeResult != null && merchantScopeResult.isSuccess() && merchantScopeResult != null && !merchantScopeResult.getValue().isEmpty()) {
                    List<Long> scopeIds = new ArrayList<>();
                    for (MerchantScopeDO merchantScopeDO : merchantScopeResult.getValue()) {
                        scopeIds.add(merchantScopeDO.getBusinessScopeId());
                    }
                    if(!scopeIds.isEmpty()) {
                        businessScopeQueryDTOQUery = new BusinessScopeQueryDTO();
                        businessScopeQueryDTOQUery.setDomainId(Constant.DOMAIN_JIUXIU);
                        businessScopeQueryDTOQUery.getIdSet().addAll(scopeIds);
                        MemResult<List<BusinessScopeDO>> businessScopeResult = businessScopeService.findBusinessScopesByScope(businessScopeQueryDTOQUery);

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
                        MerchantCategoryQueryDTO merchantCategoryQueryDTOQuery = new MerchantCategoryQueryDTO();
                        merchantCategoryQueryDTOQuery.setDomainId(Constant.DOMAIN_JIUXIU);
                        merchantCategoryQueryDTOQuery.setId(result.getValue().getMerchantCategoryId());
                        MemResult<List<MerchantCategoryDO>> merchantCategoryResult = merchantApplyService.getMerchantCategory(merchantCategoryQueryDTOQuery);
                        if (merchantCategoryResult != null && merchantCategoryResult.isSuccess() && merchantCategoryResult.getValue() != null && !merchantCategoryResult.getValue().isEmpty()) {
                            MerchantCategoryDO merchantCategoryDO = merchantCategoryResult.getValue().get(0);
                            long parentId = merchantCategoryDO.getParentId();
                            StringBuilder builder = new StringBuilder();
                            List<String> names = new ArrayList<>();
                            while (parentId > 0) {
                                merchantCategoryQueryDTOQuery.setId(parentId);
                                MemResult<List<MerchantCategoryDO>> temp = merchantApplyService.getMerchantCategory(merchantCategoryQueryDTOQuery);
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
                        model.addAttribute("feature", result.getValue().getIsDirectSale() == 0 ? ExamineCharacter.DIRECT_SALE.getName() : ExamineCharacter.BOUTIQUE.getName());
                        model.addAttribute("type", result.getValue().getType());
                        model.addAttribute("status", result.getValue().getExaminStatus());

                        // 根据sellerId查询商家的商品类目
                        MemResult<List<MerchantItemCategoryDO>> merchantItemCategoryResult = merchantItemCategoryService.findMerchantItemCategoriesBySellerId(Constant.DOMAIN_JIUXIU, result.getValue().getSellerId());

                        if (merchantItemCategoryResult != null && merchantItemCategoryResult.isSuccess() && !merchantItemCategoryResult.getValue().isEmpty()) {

                            // 加载商品类目id
                            long[] categoryIds = new long[merchantItemCategoryResult.getValue().size()];
                            List<MerchantItemCategoryDO> merchantItemCategoryDOs = merchantItemCategoryResult.getValue();
                            for (int i = 0; i < merchantItemCategoryDOs.size(); i++) {
                                categoryIds[i] = merchantItemCategoryDOs.get(i).getItemCategoryId();
                            }

                            MemResult<List<ScopeItemCategoryDO>> scopeItemCategoryResult = scopeItemCategoryService.findScopeItemCategoriesByCategory(Constant.DOMAIN_JIUXIU, categoryIds);
                            if(scopeItemCategoryResult != null && scopeItemCategoryResult.isSuccess() && scopeItemCategoryResult.getValue() != null && !scopeItemCategoryResult.getValue().isEmpty()) {
                                Map<String, List<CategoryVO>> itemCategoryMap = new LinkedHashMap<>();
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
                    }
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
        MemResult<ExamineInfoDTO> examineInfoDTOResult = examineDealServiceRef.queryMerchantExamineInfoById(id);
        if(null == examineInfoDTOResult || !examineInfoDTOResult.isSuccess() || null == examineInfoDTOResult.getValue()) {
            // TODO 跳转错误页面处理
            return "";
        }

        BusinessScopeQueryDTO businessScopeQueryDTOQUery = new BusinessScopeQueryDTO();
        businessScopeQueryDTOQUery.setDomainId(Constant.DOMAIN_JIUXIU);
        businessScopeQueryDTOQUery.setSellerId(examineInfoDTOResult.getValue().getSellerId());
        MemResult<List<MerchantScopeDO>> merchantScopeResult = businessScopeService.getMerchantScope(businessScopeQueryDTOQUery);
        if(!merchantScopeResult.isSuccess()) {
            // TODO 跳转错误页面处理
            return "";
        }
        List<Long> scopeIds = new ArrayList<>();
        for (MerchantScopeDO merchantScopeDO : merchantScopeResult.getValue()) {
            scopeIds.add(merchantScopeDO.getBusinessScopeId());
        }
        if(!scopeIds.isEmpty()) {
            // 找到商户的经营范围
            BusinessScopeQueryDTO businessScopeQueryDTOQuery = new BusinessScopeQueryDTO();
            businessScopeQueryDTOQuery.getIdSet().addAll(scopeIds);
            businessScopeQueryDTOQuery.setDomainId(Constant.DOMAIN_JIUXIU);
            MemResult<List<BusinessScopeDO>> businessScopeMemResult = businessScopeService.findBusinessScopesByScope(businessScopeQueryDTOQuery);
            if(null == businessScopeMemResult || !businessScopeMemResult.isSuccess() || null == businessScopeMemResult.getValue() || businessScopeMemResult.getValue().isEmpty()) {
                // TODO 跳转错误页面处理
                return "";
            }

            long[] businessScopeIds = new long[businessScopeMemResult.getValue().size()];
            Map<String, List<CategoryVO>> result = new LinkedHashMap<>();
            for (int i = 0; i < businessScopeMemResult.getValue().size(); i++) {
                businessScopeIds[i] = businessScopeMemResult.getValue().get(i).getId();
            }
            MemResult<List<ScopeItemCategoryDO>> scopeItemCategoryMemResult = scopeItemCategoryService.findScopeItemCategoriesByMerchantScope(Constant.DOMAIN_JIUXIU, businessScopeIds);
            if (null == scopeItemCategoryMemResult || !scopeItemCategoryMemResult.isSuccess() || null == scopeItemCategoryMemResult.getValue() || scopeItemCategoryMemResult.getValue().isEmpty()) {
                // TODO 跳转错误页面处理
                return "";
            }
            outer:
            for (ScopeItemCategoryDO scopeItemCategoryDO : scopeItemCategoryMemResult.getValue()) {
                List<CategoryDO> categoryDOs = categoryService.getCategoryDOList(scopeItemCategoryDO.getItemCategoryId());
                if(null == categoryDOs || categoryDOs.isEmpty()) {
                    categoryDOs = new ArrayList<>();
                    try {
                        CategoryDO categoryDO = categoryService.getCategoryDOById(scopeItemCategoryDO.getItemCategoryId());
                        if (categoryDO != null) {
                            categoryDOs.add(categoryDO);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        // TODO 跳转错误页面处理
                        return "";
                    }
                }

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
                        continue outer;
                    }
                }
            }
            model.addAttribute("examineId", id);
            model.addAttribute("scopeCategories", result);
        }

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
