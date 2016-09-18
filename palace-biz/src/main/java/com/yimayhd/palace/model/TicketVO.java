package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.ic.client.model.domain.TicketDO;

/**
 * Created by hongfei.guo on 2016/05/23.
 */
public class TicketVO extends TicketDO{

	private static final long serialVersionUID = 1L;

	public static TicketDO getScenicDO(TicketVO ticketVO){
		TicketDO ticketDO = new TicketDO();
        BeanUtils.copyProperties(ticketVO, ticketDO);
        return ticketDO;
    }
	
	public static TicketVO getScenicVO(TicketDO ticketDO){
		TicketVO ticketVO = new TicketVO();
        BeanUtils.copyProperties(ticketDO, ticketVO);
        return ticketVO;
    }
}
