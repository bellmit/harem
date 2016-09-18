package com.yimayhd.palace.model.vo;

import com.yimayhd.palace.model.TravelOfficial;
import com.yimayhd.palace.model.query.TravelOfficialListQuery;

/**
 * Created by Administrator on 2015/11/10.
 */
public class TravelOfficialVO {
    private TravelOfficial travelOfficial;
    private TravelOfficialListQuery travelOfficialListQuery;

    public TravelOfficial getTravelOfficial() {
        return travelOfficial;
    }

    public void setTravelOfficial(TravelOfficial travelOfficial) {
        this.travelOfficial = travelOfficial;
    }

    public TravelOfficialListQuery getTravelOfficialListQuery() {
        return travelOfficialListQuery;
    }

    public void setTravelOfficialListQuery(TravelOfficialListQuery travelOfficialListQuery) {
        this.travelOfficialListQuery = travelOfficialListQuery;
    }
}
