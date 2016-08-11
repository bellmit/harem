package com.yimayhd.palace.controller.jiuxiu.apply;


import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.CategoryDO;
import com.yimayhd.membercenter.client.domain.MerchantScopeDO;
import com.yimayhd.membercenter.client.domain.merchant.*;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.query.BusinessScopeQueryDTO;
import com.yimayhd.membercenter.client.query.InfoQueryDTO;
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
import com.yimayhd.membercenter.enums.CertificateType;
import com.yimayhd.membercenter.enums.ExamineCharacter;
import com.yimayhd.membercenter.enums.ExamineStatus;
import com.yimayhd.membercenter.enums.ExamineType;
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
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.user.client.cache.CityDataCacheClient;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.query.MerchantQuery;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.DataCacheService;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.session.manager.SessionManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private CityDataCacheClient cityDataCacheClient;
    @Autowired
    private MerchantService merchantService;

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
        BizResultSupport approveResultSupport = new BizResultSupport();
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        MemResult<ExamineInfoDTO> examineInfoDTOResult = examineDealServiceRef.queryMerchantExamineInfoById(approveVO.getId());
        int type = examineInfoDTOResult.getValue().getType();
        // 如果审核通过并且身份是商户,此处不做任何处理,待商品类目分配完成后集中更新申请状态,新增申请明细,新增商户,新增商户与商品类目关系,发送短信
        if (approveVO.isPass() && type == MerchantType.MERCHANT.getType()) {
        	//验证银行账户
//            BizResultSupport checkBankResult = applyBiz.checkCorBankAccount(examineInfoDTOResult.getValue());
//            if (checkBankResult == null || !checkBankResult.isSuccess()) {
//    			log.error("applyBiz.checkCorBankAccount result:{}",JSON.toJSONString(checkResult));
//    			approveResultSupport.setPalaceReturnCode(PalaceReturnCode.VERIFY_BANK_INFO_ERROR);
//    			return approveResultSupport;
//    		}
            
            checkResult = new BizResultSupport();
            return checkResult;
        }
        // 1,驳回;2,身份为达人进行直接执行1期的审批操作
        long userId = sessionManager.getUserId();
        BizResultSupport result = applyBiz.approve(approveVO, userId);
        return result;
    }

    @RequestMapping(value = "detail")
    public String getMerchantDetail(Model model, long id ,String sellerId) {
        try {
        	MemResult<ExamineInfoDTO> result = null;
        	if(id !=0){
        		result = examineDealServiceRef.queryMerchantExamineInfoById(id);
        	}else if(!StringUtils.isEmpty(sellerId)){
        		InfoQueryDTO info = new InfoQueryDTO();
        		info.setDomainId(Constant.DOMAIN_JIUXIU);
        		info.setSellerId(Long.parseLong(sellerId));
        		result = examineDealServiceRef.queryMerchantExamineInfoBySellerId(info);
        	}
            if (result.isSuccess() && null != result.getValue()) {

                model.addAttribute("examineInfo", result.getValue());
                model.addAttribute("feature", result.getValue().getIsDirectSale() == ExamineCharacter.DIRECT_SALE.getType() ? ExamineCharacter.DIRECT_SALE.getName() : ExamineCharacter.BOUTIQUE.getName());
                model.addAttribute("type", result.getValue().getType());
                model.addAttribute("status", result.getValue().getExaminStatus());
                if (!StringUtils.isEmpty(result.getValue().getAccountBankProvinceCode())) {
                    String provinceName = cityDataCacheClient.getCityByCode(result.getValue().getAccountBankProvinceCode()).getName();
                    model.addAttribute("provinceName", provinceName);
                }
                if (!StringUtils.isEmpty(result.getValue().getAccountBankCityCode())) {
                    String cityName = cityDataCacheClient.getCityByCode(result.getValue().getAccountBankCityCode()).getName();
                    model.addAttribute("cityName", cityName);
                }

                if (CertificateType.IDCARD.getType().equals(result.getValue().getPrincipleCard())) {
                    model.addAttribute("certificateType", CertificateType.IDCARD.getName());
                } else if (CertificateType.CAR_LICENSE.getType().equals(result.getValue().getPrincipleCard())) {
                    model.addAttribute("certificateType", CertificateType.CAR_LICENSE.getName());
                } else if (CertificateType.PASSPORT.getType().equals(result.getValue().getPrincipleCard())) {
                    model.addAttribute("certificateType", CertificateType.PASSPORT.getName());
                } else if (CertificateType.GUIDE_LICENSE.getType().equals(result.getValue().getPrincipleCard())) {
                    model.addAttribute("certificateType", CertificateType.GUIDE_LICENSE.getName());
                }

                List<Map<String,String>> pictures = new ArrayList<>();
                if(!StringUtils.isEmpty(result.getValue().getBusinessLicense())) {
                    Map<String,String> picture1 = new HashMap<>();
                    picture1.put("title","营业执照副本正面");
                    picture1.put("url",result.getValue().getBusinessLicense());
                    pictures.add(picture1);
                }
                if(!StringUtils.isEmpty(result.getValue().getOrgCard())) {
                    Map<String,String> picture2 = new HashMap<>();
                    picture2.put("title","组织机构代码证正面");
                    picture2.put("url",result.getValue().getOrgCard());
                    pictures.add(picture2);
                }
                if(!StringUtils.isEmpty(result.getValue().getAffairsCard())) {
                    Map<String,String> picture3 = new HashMap<>();
                    picture3.put("title","税务登记证正面");
                    picture3.put("url",result.getValue().getAffairsCard());
                    pictures.add(picture3);
                }
                if(!StringUtils.isEmpty(result.getValue().getOpenCard())) {
                    Map<String,String> picture4 = new HashMap<>();
                    picture4.put("title","开户许可证正面");
                    picture4.put("url",result.getValue().getOpenCard());
                    pictures.add(picture4);
                }
                if(!StringUtils.isEmpty(result.getValue().getTravingCard())) {
                    Map<String,String> picture5 = new HashMap<>();
                    picture5.put("title","旅行社业务经营许可证正面");
                    picture5.put("subTitle","*旅行社必填");
                    picture5.put("url",result.getValue().getTravingCard());
                    pictures.add(picture5);
                }
                if(!StringUtils.isEmpty(result.getValue().getTouchProve())) {
                    Map<String,String> picture6 = new HashMap<>();
                    picture6.put("title","联系人变更证明");
                    picture6.put("subTitle","*仅限合同签署人与联系人不一致情形时上传");
                    picture6.put("url",result.getValue().getTouchProve());
                    pictures.add(picture6);
                }
                model.addAttribute("pictures", pictures);

                if (result.getValue().getType() == MerchantType.TALENT.getType()) { // 达人
                    List<Map<String, Object>> qualificationPictures = new ArrayList<>();
                    Map<String,Object> certificateMap = new HashMap<>();
                    if(!StringUtils.isEmpty(result.getValue().getTouristCard())) {
//                    	certificateMap.put("touristCard","导游证");
//                    	certificateMap.put("touristCard_url",result.getValue().getTouristCard());
                    	certificateMap.put("导游证",result.getValue().getTouristCard());
                        qualificationPictures.add(certificateMap);
                    }

                    if(!StringUtils.isEmpty(result.getValue().getDrivingLinence())) {
                        Map<String,Object> drivingLinenceMap = new HashMap<>();
//                        certificateMap.put("drivingLinence","行驶证");
//                        certificateMap.put("drivingLinence_url",result.getValue().getDrivingLinence());
                        certificateMap.put("行驶证",result.getValue().getDrivingLinence());
                        qualificationPictures.add(certificateMap);
                    }

                    if(!StringUtils.isEmpty(result.getValue().getDivingLinence())) {
                        Map<String,Object> divingLinenceMap = new HashMap<>();
//                        certificateMap.put("divingLinence","潜水证");
//                        certificateMap.put("divingLinence_url",result.getValue().getDivingLinence());
                        certificateMap.put("潜水证",result.getValue().getDivingLinence());
                        qualificationPictures.add(certificateMap);
                    }

                    if(!StringUtils.isEmpty(result.getValue().getPhotographyCertificate())) {
                        Map<String,Object> photographyCertificateMap = new HashMap<>();
//                        certificateMap.put("title","摄影证");
//                        certificateMap.put("url",result.getValue().getPhotographyCertificate());
                        certificateMap.put("摄影证",result.getValue().getPhotographyCertificate());
                        qualificationPictures.add(certificateMap);
                    }

                    if(!StringUtils.isEmpty(result.getValue().getClimbingCertificate())) {
                        Map<String,Object> climbingCertificateMap = new HashMap<>();
//                        certificateMap.put("climbingCertificate","登山证");
//                        certificateMap.put("climbingCertificate_url",result.getValue().getClimbingCertificate());
                        certificateMap.put("登山证",result.getValue().getClimbingCertificate());
                        qualificationPictures.add(certificateMap);
                    }

                    if(!StringUtils.isEmpty(result.getValue().getTrainingCertificate())) {
                        Map<String,Object> trainingCertificateMap = new HashMap<>();
//                        certificateMap.put("trainingCertificate","健身教练证");
//                        certificateMap.put("trainingCertificate_url",result.getValue().getTrainingCertificate());
                        certificateMap.put("健身教练证",result.getValue().getTrainingCertificate());
                        qualificationPictures.add(certificateMap);
                    }

                    if(!StringUtils.isEmpty(result.getValue().getTeacherCertificate())) {
                        Map<String,Object> teacherCertificateMap = new HashMap<>();
//                        certificateMap.put("teacherCertificate","教师证");
//                        certificateMap.put("teacherCertificate_url",result.getValue().getTeacherCertificate());
                        certificateMap.put("教师证",result.getValue().getTeacherCertificate());
                        qualificationPictures.add(certificateMap);
                    }

                    if(!StringUtils.isEmpty(result.getValue().getArtCertificate())) {
                        Map<String,Object> artCertificateMap = new HashMap<>();
//                        certificateMap.put("artCertificate","美术证");
//                        certificateMap.put("artCertificate_url",result.getValue().getArtCertificate());
                        certificateMap.put("美术证",result.getValue().getArtCertificate());
                        qualificationPictures.add(certificateMap);
                    }


                    model.addAttribute("qualificationPictures", qualificationPictures);

                } else { // 商户
                    BusinessScopeQueryDTO businessScopeQueryDTOQUery = new BusinessScopeQueryDTO();
                    businessScopeQueryDTOQUery.setDomainId(Constant.DOMAIN_JIUXIU);
                    businessScopeQueryDTOQUery.setSellerId(result.getValue().getSellerId());
                    MemResult<List<MerchantScopeDO>> merchantScopeResult = businessScopeService.getMerchantScope(businessScopeQueryDTOQUery);
                    if (merchantScopeResult != null && merchantScopeResult.isSuccess() && merchantScopeResult != null && !merchantScopeResult.getValue().isEmpty()) {
                        List<Long> scopeIds = new ArrayList<>();
                        for (MerchantScopeDO merchantScopeDO : merchantScopeResult.getValue()) {
                            scopeIds.add(merchantScopeDO.getBusinessScopeId());
                        }
                        if (!scopeIds.isEmpty()) {
                            businessScopeQueryDTOQUery = new BusinessScopeQueryDTO();
                            businessScopeQueryDTOQUery.setDomainId(Constant.DOMAIN_JIUXIU);
                            businessScopeQueryDTOQUery.getIdSet().addAll(scopeIds);
                            MemResult<List<BusinessScopeDO>> businessScopeResult = businessScopeService.findBusinessScopesByScope(businessScopeQueryDTOQUery);

                            List<BusinessScopeVO> businessScopeVOs = new ArrayList<>();
                            Map<Long, String> nameMap = new LinkedHashMap<>();
                            for (BusinessScopeDO businessScopeDO : businessScopeResult.getValue()) {
                                if (businessScopeDO.getParentId() > 0) {
                                    if (!nameMap.containsKey(businessScopeDO.getParentId())) {
                                        String name = null;
                                        businessScopeQueryDTOQUery = new BusinessScopeQueryDTO();
                                        businessScopeQueryDTOQUery.setDomainId(Constant.DOMAIN_JIUXIU);
                                        businessScopeQueryDTOQUery.getIdSet().add(businessScopeDO.getParentId());
                                        MemResult<List<BusinessScopeDO>> parentResult = businessScopeService.findBusinessScopesByScope(businessScopeQueryDTOQUery);
                                        if (parentResult != null && parentResult.isSuccess() && parentResult.getValue() != null && !parentResult.getValue().isEmpty()) {
                                            name = parentResult.getValue().get(0).getName();
                                        }
                                        if (name != null) {
                                            name += " " + businessScopeDO.getName();
                                        } else {
                                            name = businessScopeDO.getName();
                                        }
                                        nameMap.put(businessScopeDO.getParentId(), name);
                                    } else {
                                        nameMap.put(businessScopeDO.getParentId(), nameMap.get(businessScopeDO.getParentId()).concat(" ").concat(businessScopeDO.getName()));
                                    }
                                } else {
                                    nameMap.put(businessScopeDO.getId(), businessScopeDO.getName());
                                }
                            }
                            for (Map.Entry<Long, String> entry : nameMap.entrySet()) {
                                BusinessScopeVO vo = new BusinessScopeVO();
                                vo.setName(entry.getValue());
                                businessScopeVOs.add(vo);
                            }
                            model.addAttribute("scope", businessScopeVOs);

                            // 查询资质,展示资质图片
                            QualificationQueryDTO qualificationQueryDTO = new QualificationQueryDTO();
                            qualificationQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
                            qualificationQueryDTO.setSellerId(result.getValue().getSellerId());
                            MemResult<List<MerchantQualificationDO>> merchantQualificationResult = qualificationService.getMerchantQualification(qualificationQueryDTO);
                            if (merchantQualificationResult != null && merchantQualificationResult.isSuccess() && merchantQualificationResult.getValue() != null && !merchantQualificationResult.getValue().isEmpty()) {
                                List<Long> ids = new ArrayList<>();
                                for (MerchantQualificationDO merchantQualificationDO : merchantQualificationResult.getValue()) {
                                    ids.add(merchantQualificationDO.getQulificationId());
                                }

                                List<Map<String, Object>> qualificationPictures = new ArrayList<>();
                                outer:
                                for (MerchantQualificationDO merchantQualificationDO : merchantQualificationResult.getValue()) {
                                    String[] urls = merchantQualificationDO.getContent().split(",");
                                    for (String url : urls) {
                                        if(StringUtils.isEmpty(url)) {
                                            continue ;
                                        }
                                        qualificationQueryDTO = new QualificationQueryDTO();
                                        qualificationQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
                                        qualificationQueryDTO.getIdSet().add(merchantQualificationDO.getQulificationId());
                                        MemResult<List<QualificationDO>> qualificationResult = qualificationService.getQualification(qualificationQueryDTO);
                                        if (qualificationResult != null && qualificationResult.isSuccess() && qualificationResult.getValue() != null && !qualificationResult.getValue().isEmpty()) {
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("url", url);
                                            map.put("title", qualificationResult.getValue().get(0).getTitle());
                                            qualificationQueryDTO = new QualificationQueryDTO();
                                            qualificationQueryDTO.setDomainId(Constant.DOMAIN_JIUXIU);
                                            qualificationQueryDTO.setMerchantCategoryId(result.getValue().getMerchantCategoryId());
                                            qualificationQueryDTO.setQualificationId(merchantQualificationDO.getQulificationId());
                                            qualificationQueryDTO.setDirectSale(result.getValue().getIsDirectSale());
                                            MemResult<Boolean> booleanMemResult = qualificationService.getQualificationRequired(qualificationQueryDTO);
                                            if (booleanMemResult != null && booleanMemResult.isSuccess() && booleanMemResult.getValue() != null) {
                                                map.put("required", booleanMemResult.getValue());
                                            } else {
                                                map.put("required", false);
                                            }
                                            qualificationPictures.add(map);
                                        }
                                    }
                                }
                                model.addAttribute("qualificationPictures", qualificationPictures);
                                result.getValue().setMerchantQualifications(merchantQualificationResult.getValue());
                            }


                            MerchantCategoryQueryDTO merchantCategoryQueryDTOQuery = new MerchantCategoryQueryDTO();
                            merchantCategoryQueryDTOQuery.setDomainId(Constant.DOMAIN_JIUXIU);
                            merchantCategoryQueryDTOQuery.setId(result.getValue().getMerchantCategoryId());
                            MemResult<List<MerchantCategoryDO>> merchantCategoryResult = merchantApplyService.getMerchantCategory(merchantCategoryQueryDTOQuery);
                            if (merchantCategoryResult != null && merchantCategoryResult.isSuccess() && merchantCategoryResult.getValue() != null && !merchantCategoryResult.getValue().isEmpty()) {
                                MerchantCategoryDO merchantCategoryDO = merchantCategoryResult.getValue().get(0);
                                long parentId = merchantCategoryDO.getParentId();
                                StringBuilder builder;
                                if (merchantCategoryDO.getType() == MerchantType.TRAVEL_AGENCY.getType()) { // 旅行社时,前面添加旅游企业

                                    builder = new StringBuilder("旅游企业 : ");
                                } else {
                                    builder = new StringBuilder();
                                }
                                List<String> names = new ArrayList<>();
                                while (parentId > 0) {
                                    merchantCategoryQueryDTOQuery.setId(parentId);
                                    MemResult<List<MerchantCategoryDO>> temp = merchantApplyService.getMerchantCategory(merchantCategoryQueryDTOQuery);
                                    if (temp.isSuccess() && temp != null && temp.getValue() != null && !temp.getValue().isEmpty()) {
                                        names.add(temp.getValue().get(0).getName());
                                        parentId = temp.getValue().get(0).getParentId();
                                    } else {
                                        break;
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
                                if (scopeItemCategoryResult != null && scopeItemCategoryResult.isSuccess() && scopeItemCategoryResult.getValue() != null && !scopeItemCategoryResult.getValue().isEmpty()) {
                                    Map<String, List<CategoryVO>> itemCategoryMap = new LinkedHashMap<>();
                                    outer:
                                    for (ScopeItemCategoryDO scopeItemCategoryDO : scopeItemCategoryResult.getValue()) {
                                        List<CategoryDO> categoryDOs = categoryService.getCategoryDOList(scopeItemCategoryDO.getItemCategoryId());
                                        if (null == categoryDOs || categoryDOs.isEmpty()) {
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
                                        for (BusinessScopeDO businessScopeDO : businessScopeResult.getValue()) {
                                            if (!itemCategoryMap.containsKey(businessScopeDO.getName()) && businessScopeDO.getStatus() != 2) {
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
        if (null == examineInfoDTOResult || !examineInfoDTOResult.isSuccess() || null == examineInfoDTOResult.getValue()) {
            // TODO 跳转错误页面处理
            return "";
        }

        BusinessScopeQueryDTO businessScopeQueryDTOQUery = new BusinessScopeQueryDTO();
        businessScopeQueryDTOQUery.setDomainId(Constant.DOMAIN_JIUXIU);
        businessScopeQueryDTOQUery.setSellerId(examineInfoDTOResult.getValue().getSellerId());
        MemResult<List<MerchantScopeDO>> merchantScopeResult = businessScopeService.getMerchantScope(businessScopeQueryDTOQUery);
        if (!merchantScopeResult.isSuccess()) {
            // TODO 跳转错误页面处理
            return "";
        }
        List<Long> scopeIds = new ArrayList<>();
        for (MerchantScopeDO merchantScopeDO : merchantScopeResult.getValue()) {
            scopeIds.add(merchantScopeDO.getBusinessScopeId());
        }
        if (!scopeIds.isEmpty()) {
            // 找到商户的经营范围
            BusinessScopeQueryDTO businessScopeQueryDTOQuery = new BusinessScopeQueryDTO();
            businessScopeQueryDTOQuery.getIdSet().addAll(scopeIds);
            businessScopeQueryDTOQuery.setDomainId(Constant.DOMAIN_JIUXIU);
            MemResult<List<BusinessScopeDO>> businessScopeMemResult = businessScopeService.findBusinessScopesByScope(businessScopeQueryDTOQuery);
            if (null == businessScopeMemResult || !businessScopeMemResult.isSuccess() || null == businessScopeMemResult.getValue() || businessScopeMemResult.getValue().isEmpty()) {
                // TODO 跳转错误页面处理
                return "";
            }

            List<ScopeItemCategoryDO> scopeItemCategoryDOs = new ArrayList<>();
            for (BusinessScopeDO businessScopeDO : businessScopeMemResult.getValue()) {
                ScopeItemCategoryDO scopeItemCategoryDO = new ScopeItemCategoryDO();
                scopeItemCategoryDO.setDomainId(Constant.DOMAIN_JIUXIU);
                scopeItemCategoryDO.setBusinessScopeId(businessScopeDO.getId());
                scopeItemCategoryDO.setMerchantCategoryId(examineInfoDTOResult.getValue().getMerchantCategoryId());
                scopeItemCategoryDOs.add(scopeItemCategoryDO);
            }
            MemResult<List<ScopeItemCategoryDO>> scopeItemCategoryMemResult = scopeItemCategoryService.findScopeItemCategories(Constant.DOMAIN_JIUXIU, scopeItemCategoryDOs);
            if (null == scopeItemCategoryMemResult || !scopeItemCategoryMemResult.isSuccess() || null == scopeItemCategoryMemResult.getValue() || scopeItemCategoryMemResult.getValue().isEmpty()) {
                return "/system/apply/allocation";
            }

            Map<String, List<CategoryVO>> result = new LinkedHashMap<>();
            outer:
            for (ScopeItemCategoryDO scopeItemCategoryDO : scopeItemCategoryMemResult.getValue()) {
                List<CategoryDO> categoryDOs = categoryService.getCategoryDOList(scopeItemCategoryDO.getItemCategoryId());
                if (null == categoryDOs || categoryDOs.isEmpty()) {
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
                    if (!result.containsKey(businessScopeDO.getName()) && businessScopeDO.getStatus() != 2) {
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

        MemResult<ExamineInfoDTO> examineInfoDTOResult = examineDealServiceRef.queryMerchantExamineInfoById(allocationVO.getExamineId());

        // 判断重名
        String merchantName = examineInfoDTOResult.getValue().getMerchantName();
        MerchantQuery merchantQuery = new MerchantQuery();
        merchantQuery.setDomainId(Constant.DOMAIN_JIUXIU);
        merchantQuery.setName(merchantName);
        BaseResult<List<MerchantDO>> merchantDOs = merchantService.getMerchantList(merchantQuery);
        if (!merchantDOs.isSuccess() ) {
            bizResultSupport.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return bizResultSupport;
        }
        if (!CollectionUtils.isEmpty(merchantDOs.getValue())) {
        	int count = 0;
			for (MerchantDO merchant : merchantDOs.getValue()) {
				if (merchant.getSellerId() == examineInfoDTOResult.getValue().getSellerId() ) {
					count ++ ;
					break;
				}
			}
			if (count == 0) {
				bizResultSupport.setPalaceReturnCode(PalaceReturnCode.MUTI_MERCHANT_FAILED);
	            return bizResultSupport;
			}
		}
        
        String[] array = allocationVO.getCategoryIds().split(",");
        long[] categoryIds = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            categoryIds[i] = Long.parseLong(array[i]);
        }
        MemResultSupport memResultSupport = merchantItemCategoryService.saveMerchantItemCategories(Constant.DOMAIN_JIUXIU, allocationVO.getExamineId(), categoryIds);
        if (!memResultSupport.isSuccess()) {
            bizResultSupport.setPalaceReturnCode(PalaceReturnCode.MERCHANT_BIND_FAILED);
            return bizResultSupport;
        }

        return new BizResultSupport();

    }
    @RequestMapping(value="verifyBankInfo")
    @ResponseBody
    public BizResultSupport verifyBankInfo(HttpServletRequest request,long examineId) {
    	BizResultSupport result = new BizResultSupport();
        MemResult<ExamineInfoDTO> examineInfoDTOResult = examineDealServiceRef.queryMerchantExamineInfoById(examineId);
        if (examineInfoDTOResult == null || !examineInfoDTOResult.isSuccess() || examineInfoDTOResult.getValue() == null) {
			result.setPalaceReturnCode(PalaceReturnCode.VERIFY_BANK_INFO_ERROR);
			return result;
		}
        ExamineInfoDTO examineInfoDTO = examineInfoDTOResult.getValue();
        if (examineInfoDTO.getType() == MerchantType.MERCHANT.getType()) {
			result = applyBiz.checkCorBankAccount(examineInfoDTO);
		}
        if (examineInfoDTO.getType() == MerchantType.TALENT.getType()) {
			result = applyBiz.checkEleBankAccount(examineInfoDTO);
		}
        if (result == null) {
			result.setPalaceReturnCode(PalaceReturnCode.VERIFY_BANK_INFO_ERROR);
			return result;
		}
    	return result;
    }
}
