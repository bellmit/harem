package com.yimayhd.palace.service.impl;

import com.taobao.common.tfs.TfsManager;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.vo.PushQueryVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.repo.RcDelayRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.PushService;
import com.yimayhd.palace.util.HandleFilesUtils;
import com.yimayhd.resourcecenter.domain.RcDelayPush;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
        List<PushVO> PushVOList = new ArrayList<PushVO>();
        RcDelayPushPageQuery rcDelayPushPageQuery = new RcDelayPushPageQuery();
        rcDelayPushPageQuery.setPageNo(pushQueryVO.getPageNumber());
        rcDelayPushPageQuery.setPageSize(pushQueryVO.getPageSize());
        rcDelayPushPageQuery.setNeedCount(true);

        RCPageResult<RcDelayPush> rcResult = rcDelayRepo.listMsg(rcDelayPushPageQuery);
        if(null == rcResult || !rcResult.isSuccess() ){
            return new PageVO<PushVO>();
        }
        return new PageVO<PushVO>(pushQueryVO.getPageNumber(),pushQueryVO.getPageSize(),rcResult.getTotalCount(),PushVOList);
    }

    @Override
    public boolean saveOrUpdate(PushVO pushVO) throws Exception {
        if(Constant.PUSH_SPECIFIC == pushVO.getPushModelType() && StringUtils.isNotEmpty(pushVO.getPushModelFileName())){
            String fileName = pushVO.getPushModelFileName();
            String newFileName = convertCsvToTxtPaht(fileName);
            if (StringUtils.isNotEmpty(newFileName)){
                pushVO.setPushModelFileName(newFileName);
            }
        }
        RcDelayPush dbPush = null;
        RcDelayPush rp = new RcDelayPush();
        //插入
        if(pushVO.getId()==0){
            dbPush  =  rcDelayRepo.insertMsg(rp);
            return isSuccess(dbPush);
        }
        //更新
        if(updateCheck(pushVO)){
            throw new Exception("参数校验不合法");
        }
        dbPush = rcDelayRepo.updateMsg(rp);
        return isSuccess(dbPush);

    }

    public boolean updateCheck(PushVO push){
        if(push.getStatus()==1){//枚举值
            return false;
        }
        if(null !=push.getPushDate() && push.getPushDate().getTime()<System.currentTimeMillis()){
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
        Set<String> setString = HandleFilesUtils.getDistinctString(filePath);

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
