package com.yimayhd.harem.model.vo;

import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.model.query.ClubListQuery;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ClubVO {
    private Club club;
    private ClubListQuery clubListQuery;
    private PageVo pageVo;

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public ClubListQuery getClubListQuery() {
        return clubListQuery;
    }

    public void setClubListQuery(ClubListQuery clubListQuery) {
        this.clubListQuery = clubListQuery;
    }

    public PageVo getPageVo() {
        return pageVo;
    }

    public void setPageVo(PageVo pageVo) {
        this.pageVo = pageVo;
    }
}
