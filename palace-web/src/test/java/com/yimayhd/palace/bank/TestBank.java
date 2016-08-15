package com.yimayhd.palace.bank;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.membercenter.client.domain.examine.ExamineDO;
import com.yimayhd.membercenter.client.dto.ExamineInfoDTO;
import com.yimayhd.membercenter.client.enums.topic.MemberTopic;
import com.yimayhd.palace.BaseTest;
import com.yimayhd.palace.repo.member.ExamineDealRepo;
import com.yimayhd.pay.client.model.result.ResultSupport;

public class TestBank extends BaseTest {

	@Autowired
	private ExamineDealRepo examineDealRepo;
	
	@Test
	public void testBank() {
		verifyCorBankInfo();
		//approveMQ();
	}

	

	private void verifyCorBankInfo() {
//		verifyCmpEleAccountDTO.setUserId(dto.getSellerId());
//		verifyCmpEleAccountDTO.setBankAccountType(Integer.parseInt(dto.getAccountType()));
//		verifyCmpEleAccountDTO.setOpenBankNo(dto.getSettlementCard());
//		verifyCmpEleAccountDTO.setOpenBankName(dto.getAccountBankName());
//		verifyCmpEleAccountDTO.setOpenAcctNo(dto.getAccountNum());
//		verifyCmpEleAccountDTO.setOpenAcctName(dto.getFinanceOpenName());
//		verifyCmpEleAccountDTO.setContactMobile(dto.getOpenerTel());
//		verifyCmpEleAccountDTO.setContactIdNo(dto.getOpenerCard());
//		verifyCmpEleAccountDTO.setCorpName(dto.getSellerName());
//		verifyCmpEleAccountDTO.setContactName(dto.getFinanceOpenName());
//		verifyCmpEleAccountDTO.setCorpBusiCode(dto.getSaleLicenseNumber());
		ExamineInfoDTO examineInfoDTO = new ExamineInfoDTO();
		examineInfoDTO.setSellerId(3020);
		examineInfoDTO.setAccountType("2");
		examineInfoDTO.setSettlementCard("102100009980");
		examineInfoDTO.setAccountBankName("北京市 北京市 工行");
		examineInfoDTO.setAccountNum("0200064809024643310");
		examineInfoDTO.setFinanceOpenName("北京九休信息科技有限公司");
		examineInfoDTO.setSellerName("北京九休信息科技有限公司");
		examineInfoDTO.setSaleLicenseNumber("91110102318191150G");
		examineInfoDTO.setPrincipleName("鉴权测试");
		examineInfoDTO.setPrincipleTel("13810586956");
//		examineInfoDTO.setOpenerTel("133636967145");
//		examineInfoDTO.setOpenerCard("410181199001227575");
		System.out.println("-------------------------"+JSON.toJSONString(examineInfoDTO));
		ResultSupport verifyCorBankAccount = examineDealRepo.verifyCorBankAccount(examineInfoDTO);
		System.out.println("===================="+JSON.toJSONString(verifyCorBankAccount));
		
	}
	
	
	
}
