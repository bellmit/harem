package com.yimayhd.gf.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.gf.domain.GfAgentDO;
import com.yimayhd.gf.query.GfAgentQuery;
import com.yimayhd.gf.result.GfPageResult;
import com.yimayhd.gf.result.GfResult;
import com.yimayhd.gf.service.GfAgentService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.result.BizResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by p on 10/21/16.
 */
public class GfAgentRepo {
    private static final Logger log = LoggerFactory.getLogger(GfAgentRepo.class);

    @Autowired
    private GfAgentService gfAgentService;

    public GfPageResult<GfAgentDO> getList(GfAgentQuery gfAgentQuery) {
        GfPageResult<GfAgentDO> result = new GfPageResult<GfAgentDO>();
        try {
            result = gfAgentService.queryGfAgent(gfAgentQuery);
            if(result.isSuccess()){
                return result;
            }
        } catch (Exception e) {
            log.error("getList gfAgentQuery={}, result={}, error={}", JSON.toJSONString(gfAgentQuery), JSON.toJSONString(result), JSON.toJSONString(e));
        }
        return null;
    }
    public BizResult<GfAgentDO> save(GfAgentDO gfAgentDO) {
        BizResult<GfAgentDO> result = new BizResult<GfAgentDO>();
        try {
            GfResult<GfAgentDO> gfResult = gfAgentService.saveGfAgent(gfAgentDO);
            if(gfResult.isSuccess()) {
                result.setValue(gfResult.getValue());
                return result;
            }
        } catch (Exception e) {
            log.error("getList gfAgentDO={}, result={}, error={}", JSON.toJSONString(gfAgentDO), JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
    public BizResult<GfAgentDO> getGfAgentById(long id) {
        BizResult<GfAgentDO> result = new BizResult<GfAgentDO>();
        try {
            GfResult<GfAgentDO> gfResult = gfAgentService.getGfAgentById(id);
            if(gfResult.isSuccess()) {
                result.setValue(gfResult.getValue());
                return result;
            }
            result.setPalaceReturnCode(PalaceReturnCode.REMOTE_CALL_FAILED);
        } catch (Exception e) {
            log.error("getList id={}, result={}, error={}", id, JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
    public BizResult<Boolean> updateGfAgent(GfAgentDO gfAgentDO) {
        BizResult<Boolean> result = new BizResult<Boolean>();
        try {
            GfResult<Boolean> gfResult = gfAgentService.updateGfAgent(gfAgentDO);
            if(gfResult.isSuccess()) {
                result.setValue(gfResult.getValue());
                return result;
            }
        } catch (Exception e) {
            log.error("getList gfAgentDO={}, result={}, error={}", JSON.toJSONString(gfAgentDO), JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
    public BizResult<List<GfAgentDO>> checkUniqueGfAgent(GfAgentDO gfAgentDO) {
        BizResult<List<GfAgentDO>> result = new BizResult<List<GfAgentDO>>();
        try {
            GfResult<List<GfAgentDO>> gfResult = gfAgentService.checkUniqueGfAgent(gfAgentDO);
            if(gfResult.isSuccess()) {
                result.setValue(gfResult.getValue());
                return result;
            }
        } catch (Exception e) {
            log.error("getList gfAgentDO={}, result={}, error={}", JSON.toJSONString(gfAgentDO), JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
    public BizResult<Boolean> changeAgentStatus(GfAgentDO gfAgentDO){
        BizResult<Boolean> result = new BizResult<Boolean>();
        try {
            GfResult<Boolean> gfResult = gfAgentService.changeAgentStatus(gfAgentDO);
            if(gfResult.isSuccess()) {
                result.setValue(gfResult.getValue());
                return result;
            }
            result.setPalaceReturnCode(PalaceReturnCode.REMOTE_CALL_FAILED);
        } catch (Exception e) {
            log.error("getList gfAgentDO={}, result={}, error={}", JSON.toJSONString(gfAgentDO), JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
    public BizResult<List<GfAgentDO>> queryBysearch(String search){
        BizResult<List<GfAgentDO>> result = new BizResult<List<GfAgentDO>>();
        try {
            GfResult<List<GfAgentDO>> gfResult = gfAgentService.queryBysearch(search);
            if(gfResult.isSuccess()){
                result.setValue(gfResult.getValue());
                return result;
            }
        } catch (Exception e) {
            log.error("getList search={}, resutl={}, error={}", search, JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
    public BizResult<List<GfAgentDO>> queryByFuzzy(String fuzzy){
        BizResult<List<GfAgentDO>> result = new BizResult<List<GfAgentDO>>();
        try {
            GfResult<List<GfAgentDO>> gfResult = gfAgentService.queryByFuzzy(fuzzy);
            if(gfResult.isSuccess()){
                result.setValue(gfResult.getValue());
                return result;
            }
        } catch (Exception e) {
            log.error("getList fuzzy={}, result={}, error={}", fuzzy, JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
    public BizResult<List<GfAgentDO>> queryByIds(List<Long> ids) {
        BizResult<List<GfAgentDO>> result = new BizResult<List<GfAgentDO>>();
        try {
            GfResult<List<GfAgentDO>> gfResult = gfAgentService.queryByIds(ids);
            if(gfResult.isSuccess()){
                result.setValue(gfResult.getValue());
                return result;
            }
        } catch (Exception e) {
            log.error("getList pids={}, result={}, error={}", ids, JSON.toJSONString(result), JSON.toJSONString(e));
            result.setCode(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode());
        }
        return result;
    }
}
