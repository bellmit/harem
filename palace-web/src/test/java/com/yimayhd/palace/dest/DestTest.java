package com.yimayhd.palace.dest;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.BaseTest;
import com.yimayhd.resourcecenter.dto.DestinationNode;
import com.yimayhd.resourcecenter.model.enums.DestinationOutType;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.DestinationService;

public class DestTest extends BaseTest {

	@Autowired
	private DestinationService destinationServiceRef;
	
	@Test
	public void test() {
		testDest();
	}

	private void testDest() {
		RcResult<List<DestinationNode>> queryInlandDestinationTree = destinationServiceRef.queryInlandDestinationTree(DestinationOutType.EAT.getCode(), 1200);
		System.out.println(JSON.toJSONString(queryInlandDestinationTree));
	}
}
