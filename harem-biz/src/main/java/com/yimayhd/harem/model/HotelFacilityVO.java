package com.yimayhd.harem.model;

import com.yimayhd.ic.client.model.domain.FacilityIconDO;

public class HotelFacilityVO extends FacilityIconDO {

	private static final long serialVersionUID = 6110740194677588502L;
	private byte checked;

	public byte getChecked() {
		return checked;
	}

	public void setChecked(byte checked) {
		this.checked = checked;
	}
	
}
