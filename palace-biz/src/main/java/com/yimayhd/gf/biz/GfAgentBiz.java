package com.yimayhd.gf.biz;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.gf.domain.GfAgentDO;
import com.yimayhd.gf.enums.GfAgentLevel;
import com.yimayhd.gf.enums.GfAgentStatus;
import com.yimayhd.gf.model.query.GfAgentPageQuery;
import com.yimayhd.gf.model.query.GfAgentVO;
import com.yimayhd.gf.query.GfAgentQuery;
import com.yimayhd.gf.repo.GfAgentRepo;
import com.yimayhd.gf.result.GfPageResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by p on 10/21/16.
 */
public class GfAgentBiz {
    private static final Logger log = LoggerFactory.getLogger(GfAgentBiz.class);
    @Autowired
    private GfAgentRepo gfAgentRepo;

    public PageVO<GfAgentVO> getList(GfAgentPageQuery gfAgentPageQuery){
        GfAgentQuery gfAgentQuery = new GfAgentQuery();
        gfAgentQuery.setPageSize(gfAgentPageQuery.getPageSize());
        gfAgentQuery.setPageNo(gfAgentPageQuery.getPageNumber());
        if(!StringUtils.isBlank(gfAgentPageQuery.getAgentLevel())) {
            int level = GfAgentLevel.getByName(gfAgentPageQuery.getAgentLevel()).getLevel();
            gfAgentQuery.setLevelId(level);
        }
        if(null!=gfAgentPageQuery.getAgentStatus() && gfAgentPageQuery.getAgentStatus()>0) {
            gfAgentQuery.setStatus(gfAgentPageQuery.getAgentStatus());
        }
        if(!StringUtils.isBlank(gfAgentPageQuery.getAgentName())) {
            gfAgentQuery.setName(gfAgentPageQuery.getAgentName());
        }
        if(!StringUtils.isBlank(gfAgentPageQuery.getParentName())) {
            gfAgentQuery.setParentName(gfAgentPageQuery.getParentName());
        }
        try {
            if(!StringUtils.isBlank(gfAgentPageQuery.getStartAt())) {
                Date startAt = DateUtil.formatMinTimeForDate(gfAgentPageQuery.getStartAt());
                gfAgentQuery.setStartAt(startAt);
            }
            if(!StringUtils.isBlank(gfAgentPageQuery.getEndAt())) {
                Date endAt = DateUtil.formatMaxTimeForDate(gfAgentPageQuery.getEndAt());
                gfAgentQuery.setEndAt(endAt);
            }
        } catch (Exception e) {
            log.error("formatMinTimeForDateError gfAgentPageQuery={}, e={}", JSON.toJSONString(gfAgentPageQuery), JSON.toJSONString(e));
        }
        GfPageResult<GfAgentDO> gfResult = gfAgentRepo.getList(gfAgentQuery);
        if(null == gfResult || !gfResult.isSuccess()) {
            log.error("gfAgentPageQuery={}, gfResult={}", JSON.toJSONString(gfAgentPageQuery), JSON.toJSONString(gfResult));
            return null;
        }
        List<GfAgentDO> agents = gfResult.getList();
        List<Long> pids = new ArrayList<Long>();
        for(GfAgentDO agent: agents){
            long pid = agent.getParentId();
            pids.add(pid);
        }
        Map<Long,GfAgentDO> pAgents = queryByIds(pids);
        List<GfAgentVO> gfAgentVOList = new ArrayList<GfAgentVO>();
        for(GfAgentDO gfAgentDO:agents){
            GfAgentDO parent = pAgents.get(gfAgentDO.getParentId());
            gfAgentDO.setParentMobile(parent.getMobile());
            GfAgentVO gfAgentVO = convertDO2VO(gfAgentDO);
            gfAgentVOList.add(gfAgentVO);
        }
        PageVO<GfAgentVO> pageResult = new PageVO<GfAgentVO>(gfResult.getPageNo(), gfResult.getPageSize(),
                gfResult.getTotalCount(), gfAgentVOList);
        return pageResult;
    }

    public Map<Long,GfAgentDO> queryByIds(List<Long> pids){
        Map<Long,GfAgentDO> result = new HashMap<Long, GfAgentDO>();
        if(pids.size()>0) {
            BizResult<List<GfAgentDO>> pAgents = gfAgentRepo.queryByIds(pids);
            List<GfAgentDO> agents = pAgents.getValue();
            for (GfAgentDO agent : agents) {
                result.put(agent.getId(), agent);
            }
        }
        return result;
    }

    public GfAgentVO saveAgent(GfAgentVO gfAgentVO){
        GfAgentDO gfAgentDO = convertVO2DO(gfAgentVO);
        gfAgentDO.setStatus(GfAgentStatus.ABLE_STATUS.getStatus());
        BizResult<GfAgentDO> saveAgent = gfAgentRepo.save(gfAgentDO);
        if(saveAgent.isSuccess()&&null!=saveAgent.getValue()) {
            GfAgentDO saveDO = saveAgent.getValue();
            gfAgentVO = convertDO2VO(saveDO);
            return gfAgentVO;
        }
        return null;
    }

    public boolean updateAgent(GfAgentVO gfAgentVO){
        GfAgentDO gfAgentDO = convertVO2DO(gfAgentVO);
        BizResult<Boolean> saveAgent = gfAgentRepo.updateGfAgent(gfAgentDO);
        if(saveAgent.isSuccess()&&null!=saveAgent.getValue()&&saveAgent.getValue()){
            return true;
        }
        return false;
    }

    public BizResult<Boolean> updateStatus(GfAgentVO gfAgentVO){
        GfAgentDO gfAgentDO = convertVO2DO(gfAgentVO);
        BizResult<Boolean> result = gfAgentRepo.changeAgentStatus(gfAgentDO);
        return result;
    }

    public BizResult<List<GfAgentVO>> checkAgent(GfAgentVO gfAgentVO){
        BizResult<List<GfAgentVO>> result = new BizResult<List<GfAgentVO>>();
        GfAgentDO gfAgentDO = convertVO2DO(gfAgentVO);
        BizResult<List<GfAgentDO>> gfResult = gfAgentRepo.checkUniqueGfAgent(gfAgentDO);
        List<GfAgentDO> agentDOs = gfResult.getValue();
        List<GfAgentVO> agentVOList = new ArrayList<GfAgentVO>();
        long id = 0;
        if(null!=gfAgentVO.getId()&&gfAgentVO.getId()>0){
            id = gfAgentVO.getId();
        }
        for (GfAgentDO agentDO: agentDOs) {
            if(agentDO.getId()==id){
                continue;
            }
            GfAgentVO gfAgent = convertDO2VO(agentDO);
            agentVOList.add(gfAgent);
        }
        result.setValue(agentVOList);
        return result;
    }

    public BizResult<List<GfAgentVO>> searchAgent(String search){
        BizResult<List<GfAgentVO>> result = new BizResult<List<GfAgentVO>>();
        BizResult<List<GfAgentDO>> gfResult = gfAgentRepo.queryByFuzzy(search);
        List<GfAgentDO> agents = gfResult.getValue();
        List<GfAgentVO> agentVOList = new ArrayList<GfAgentVO>();
        if(null!=agents) {
            for (GfAgentDO agent : agents) {
                GfAgentVO gfAgentVO = convertDO2VO(agent);
                agentVOList.add(gfAgentVO);
            }
        }
        result.setValue(agentVOList);
        return result;
    }

    public GfAgentVO getAgent(long id){
        BizResult<GfAgentDO> gfResult = gfAgentRepo.getGfAgentById(id);
        GfAgentDO gfAgentDO = gfResult.getValue();
        GfAgentVO gfAgentVO = convertDO2VO(gfAgentDO);
        return gfAgentVO;
    }

    private GfAgentVO convertDO2VO(GfAgentDO gfAgentDO) {
        GfAgentVO  result = new GfAgentVO();

        if(gfAgentDO.getId()>0) {
            result.setId(gfAgentDO.getId());
        }
        result.setAgentName(gfAgentDO.getName());
        result.setAvatar(gfAgentDO.getAvatar());
        result.setGender(gfAgentDO.getGender());

        result.setIdNum(gfAgentDO.getIdNum());
        result.setLicense(gfAgentDO.getLicense());
        result.setWeixin(gfAgentDO.getWeixin());
        result.setMobile(gfAgentDO.getMobile());
        if(gfAgentDO.getLevelId()>0) {
            GfAgentLevel level = GfAgentLevel.getByLevel(gfAgentDO.getLevelId());
            result.setAgentLevel(level.name());
        }
        result.setStatus(gfAgentDO.getStatus());

        try {
            String start = DateUtil.convertDateToString(gfAgentDO.getStartAt());
            result.setStartAt(start);
            String end = DateUtil.convertDateToString(gfAgentDO.getEndAt());
            result.setEndAt(end);
            String created = DateUtil.convertDateToString(gfAgentDO.getGmtCreated());
            result.setCreateAt(created);
        } catch (Exception e) {
            log.error("convertDO2VO gfAgentDO={},e={}", JSON.toJSONString(gfAgentDO), JSON.toJSONString(e));
        }
        result.setParentMobile(gfAgentDO.getParentMobile());
        result.setProvinceCode(gfAgentDO.getProvinceCode());
        result.setProvinceName(gfAgentDO.getProvinceName());
        result.setParentId(gfAgentDO.getParentId());
        result.setParentName(gfAgentDO.getParentName());
        result.setParentMobile(gfAgentDO.getParentMobile());
        result.setVersion(gfAgentDO.getVersion());
        return result;
    }

    private GfAgentDO convertVO2DO(GfAgentVO gfAgentVO) {
        GfAgentDO result = new GfAgentDO();
        result.setId(gfAgentVO.getId());

        result.setName(gfAgentVO.getAgentName());
        result.setAvatar(gfAgentVO.getAvatar());
        if(null!=gfAgentVO.getGender()) {
            result.setGender(gfAgentVO.getGender());
        }
        result.setIdNum(gfAgentVO.getIdNum());
        result.setLicense(gfAgentVO.getLicense());
        result.setWeixin(gfAgentVO.getWeixin());
        result.setMobile(gfAgentVO.getMobile());

        if(!StringUtils.isBlank(gfAgentVO.getAgentLevel())) {
            int level = GfAgentLevel.getByName(gfAgentVO.getAgentLevel()).getLevel();
            result.setLevelId(level);
        }
        if(null!=gfAgentVO.getStatus()) {
            result.setStatus(gfAgentVO.getStatus());
        }

        result.setProvinceCode(gfAgentVO.getProvinceCode());
        result.setProvinceName(gfAgentVO.getProvinceName());
        if(null!=gfAgentVO.getParentId()) {
            result.setParentId(gfAgentVO.getParentId());
        }
        result.setParentName(gfAgentVO.getParentName());

        try {
            if(!StringUtils.isBlank(gfAgentVO.getStartAt())){
                Date start = DateUtil.convertStringToDate(gfAgentVO.getStartAt());
                result.setStartAt(start);
            }
            if(!StringUtils.isBlank(gfAgentVO.getEndAt())){
                Date end = DateUtil.convertStringToDate(gfAgentVO.getEndAt());
                result.setEndAt(end);
            }
        } catch (Exception e) {
            log.error("convertVO2DO gfAgentVO={},e={}", JSON.toJSONString(gfAgentVO), JSON.toJSONString(e));
        }

        if(null!=gfAgentVO.getVersion()) {
            result.setVersion(gfAgentVO.getVersion());
        }
        return result;
    }

}
