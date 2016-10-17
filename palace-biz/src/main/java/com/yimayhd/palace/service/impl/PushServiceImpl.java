package com.yimayhd.palace.service.impl;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.convert.RcDelayPushConverter;
import com.yimayhd.palace.model.vo.PushQueryVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.repo.RcDelayRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.PushService;
import com.yimayhd.palace.util.HandleFilesUtils;
import com.yimayhd.resourcecenter.domain.RcDelayPush;
import com.yimayhd.resourcecenter.model.enums.RcDelayStatus;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author create by yushengwei on 2016/10/13
 * @Description
 */
public class PushServiceImpl implements PushService {
    private static final Logger log = LoggerFactory.getLogger(PushServiceImpl.class);
    @Autowired RcDelayRepo rcDelayRepo;
    @Autowired
    private TfsManager tfsManager;
    @Value("${palace.tfsRootPath}")
    private String tfsRootPath ;


    @Override
    public PageVO<PushVO> getList(PushQueryVO pushQueryVO) throws Exception {
        List<PushVO> pushVOList = new ArrayList<PushVO>();
        RcDelayPushPageQuery rcDelayPushPageQuery = new RcDelayPushPageQuery();
        rcDelayPushPageQuery.setPageNo(pushQueryVO.getPageNumber());
        rcDelayPushPageQuery.setPageSize(pushQueryVO.getPageSize());
        rcDelayPushPageQuery.setNeedCount(true);
        rcDelayPushPageQuery.setStatus(RcDelayStatus.ALL.getCode());

        RCPageResult<RcDelayPush> rcResult = null;
        if(Constant.PUSH_MSG == pushQueryVO.getPushType()){
            rcResult = rcDelayRepo.listMsg(rcDelayPushPageQuery);
        }else if(Constant.PUSH_PUSH== pushQueryVO.getPushType()){
            rcResult = rcDelayRepo.listPush(rcDelayPushPageQuery);
        }
        if(null == rcResult || !rcResult.isSuccess() && CollectionUtils.isEmpty(rcResult.getList())){
            return new PageVO<PushVO>();
        }
        List<RcDelayPush> list =  rcResult.getList();
        pushVOList = getPushVoList(list);
        return new PageVO<PushVO>(pushQueryVO.getPageNumber(),pushQueryVO.getPageSize(),rcResult.getTotalCount(),pushVOList);
    }

    public List<PushVO> getPushVoList(List<RcDelayPush> list ){
        List<PushVO> pushVOList = new ArrayList<PushVO>();
        for (RcDelayPush rc :list ) {
            PushVO pv = RcDelayPushConverter.convertRcDelayPushToPushVo(rc);
            pushVOList.add(pv);
        }
        return pushVOList;
    }

    @Override
    public boolean saveOrUpdate(PushVO pushVO) throws Exception {
        if(Constant.PUSH_SPECIFIC == pushVO.getPushModelType() && StringUtils.isNotEmpty(pushVO.getPushModelFilePath())){
            String fileName = pushVO.getPushModelFilePath();
            String newFileName = convertCsvToTxtPaht(fileName);
            if (StringUtils.isNotEmpty(newFileName)){
                pushVO.setTransformFileUrl(newFileName);
            }
        }
        if(pushVO.getId()==0){
            return save(pushVO);
        }
        return update(pushVO);
    }

    //插入
    private boolean save(PushVO pushVO) throws Exception {
        RcDelayPush dbPush = null;
        RcDelayPush webDp = RcDelayPushConverter.convertPushVOToRcDelayPush(pushVO);
        dbPush  =  rcDelayRepo.insertMsg(webDp);
        return isSuccess(dbPush);
    }

    //更新
    private boolean update(PushVO pushVO) throws Exception {
        RcDelayPush dbPush = null;
        RcDelayPush webDp = RcDelayPushConverter.convertPushVOToRcDelayPush(pushVO);
        if(updateCheck(pushVO)){
            throw new Exception("参数校验不合法");
        }
        dbPush = rcDelayRepo.getById(webDp.getId());
        if(null == dbPush){
            throw new Exception("数据不存在");
        }
        dbPush = rcDelayRepo.updateMsg(webDp);
        return isSuccess(dbPush);
    }

    public boolean updateCheck(PushVO push){
        if(push.getStatus() != RcDelayStatus.NO_PUSH.getCode()){//不是在待发送状态下
            return false;
        }
        if(null !=push.getPushDate() && push.getPushDate().getTime() > System.currentTimeMillis()){//时间要大于当前时间
            return false;
        }
        return true;
    }

    public boolean isSuccess(RcDelayPush rcDelayPush){
        if(null == rcDelayPush){
            return false;
        }
        return true;
    }

    //发送给部分人，先从tfs中获取csv文件，在把csv转成txt传tfs,在得到地址
    public String convertCsvToTxtPaht(String fileName){
        String txtFileName="";
        String filePath = tfsRootPath + fileName;
        Set<String> setString = HandleFilesUtils.getDistinctStringFromTFS(filePath);

        if(null != setString && setString.size()>0){
            txtFileName = fileUploadToTFS(setString);
        }
        return txtFileName;
    }

    //把文件转成txt后上传到tfs返回一个地址
    public String fileUploadToTFS(Set<String> str){
        StringBuilder sb = new StringBuilder();
        for (String s:str) {
            sb.append(s).append("\n");
        }
        byte[] bytes = sb.toString().getBytes();
        String tfsCode = tfsManager.saveFile(bytes, null, ".txt");
        return tfsCode;
    }
}
