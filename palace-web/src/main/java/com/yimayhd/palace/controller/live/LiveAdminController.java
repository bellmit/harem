package com.yimayhd.palace.controller.live;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.live.client.domain.record.CloseLiveRoomDTO;
import com.yimayhd.live.client.domain.record.UpdateLiveOrderDTO;
import com.yimayhd.live.client.domain.record.UpdateLiveRecordStatusDTO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.LiveAdminBiz;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.LiveAdmin.LiveRecordVO;
import com.yimayhd.palace.model.LiveAdmin.LiveRoomVO;
import com.yimayhd.palace.model.query.LiveAdminQuery;
import com.yimayhd.palace.model.query.LiveRoomQuery;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.LiveAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.yimayhd.live.client.enums.LiveStatus.REPLAY_LIVE;
import static com.yimayhd.live.client.enums.LiveStatus.START_LIVE;


/**
 * 直播列表管理
 * <p>
 * Created by haozhu on 16/9/21.
 **/
@Controller
@RequestMapping("/jiuxiu/liveAdmin")
public class LiveAdminController extends BaseController {

    protected Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private LiveAdminBiz liveAdminBiz;
    @Resource
    private LiveAdminService liveAdminService;

    // 直播列表
    /**
     * select获取直播列表
     */
    @RequestMapping(value = "/liveAdminList", method = RequestMethod.GET)
    public String liveList(Model model, LiveAdminQuery liveAdminQuery) throws Exception {
        try {
            liveAdminQuery.setLiveStatus(START_LIVE.getStatus());
            PageVO<LiveRecordVO> pageVo = liveAdminBiz.getPageLiveRecord(liveAdminQuery);
            model.addAttribute("pageVo", pageVo);
            model.addAttribute("liveAdminQuery", liveAdminQuery);
            model.addAttribute("itemList", pageVo.getItemList());
            // 获取直播分类
            List<ComTagDO> comTagDOList = liveAdminService.getTagListByTagType(TagType.LIVESHOW);
            model.addAttribute("comTagDOList", comTagDOList);
            return "/system/LiveAdmin/LiveAdminList";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "/error";
        }
    }

    /**
     * update 权重设置
     **/
    @RequestMapping(value = "/updateLiveOrder", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> updateLiveOrder(UpdateLiveOrderDTO updateLiveOrderDTO) {
        BizResult<String> bizResult = new BizResult<>();
        try {
            bizResult = liveAdminService.updateLiveOrderById(updateLiveOrderDTO);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return bizResult;
        }
    }

    // 回放管理
    /**
     * select获取直播回放列表
     */
    @RequestMapping(value = "/playBackList", method = RequestMethod.GET)
    public String playBackList(Model model, LiveAdminQuery liveAdminQuery) throws Exception {
        try {
            liveAdminQuery.setLiveStatus(REPLAY_LIVE.getStatus());
            PageVO<LiveRecordVO> pageVo = liveAdminBiz.getPageLiveRecord(liveAdminQuery);
            Model pageVo1 = model.addAttribute("pageVo", pageVo);
            model.addAttribute("liveAdminQuery", liveAdminQuery);
            model.addAttribute("itemList", pageVo.getItemList());
            // 获取直播分类
            List<ComTagDO> comTagDOList = liveAdminService.getTagListByTagType(TagType.LIVESHOW);
            model.addAttribute("comTagDOList", comTagDOList);
            return "/system/LiveAdmin/LiveAdminPlayBackList";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "/error";
        }
    }

    /**
     * update 上架 和 下架
     **/
    @RequestMapping(value = "/updateLiveRecordStatus", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> updateLiveRecordStatus(UpdateLiveRecordStatusDTO updateLiveRecordStatusDTO) {
        BizResult<String> bizResult = new BizResult<>();
        try {
            bizResult = liveAdminService.updateLiveRecordStatus(updateLiveRecordStatusDTO);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return bizResult;
        }
    }

    // 房间管理
    /**
     * select获取直播房间列表
     */
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public String rooms(Model model, LiveRoomQuery liveRoomQuery) throws Exception {
        try {
            PageVO<LiveRoomVO> pageVo = liveAdminBiz.getPageLiveRoom(liveRoomQuery);
            model.addAttribute("pageVo", pageVo);
            model.addAttribute("liveRoomQuery", liveRoomQuery);
            model.addAttribute("itemList", pageVo.getItemList());
            return "/system/LiveAdmin/LiveAdminRooms";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "/error";
        }
    }

    /**
     * update 关闭直播间
     **/
    @RequestMapping(value = "/closeLiveRoom", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> closeLiveRoom(CloseLiveRoomDTO closeLiveRoomDTO) {
        BizResult<String> bizResult = new BizResult<>();
        try {
            bizResult = liveAdminService.closeLiveRoom(closeLiveRoomDTO);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return bizResult;
        }
    }

    /**
     * update 恢复直播间
     **/
    @RequestMapping(value = "/recoverLiveRoom", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> recoverLiveRoom(long liveRoomId) {
        BizResult<String> bizResult = new BizResult<>();
        try {
            bizResult = liveAdminService.recoverLiveRoom(liveRoomId);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return bizResult;
        }
    }
}

