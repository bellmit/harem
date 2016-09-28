package com.yimayhd.palace.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.PayAuditOrderVO;
import com.yimayhd.palace.model.PayAuditResultVO;
import com.yimayhd.palace.model.query.AuditQuery;
import com.yimayhd.palace.repo.AuditRepo;
import com.yimayhd.palace.service.AuditService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.pay.client.model.domain.audit.PayAuditOrderDO;
import com.yimayhd.pay.client.model.domain.audit.PayAuditResultDO;
import com.yimayhd.pay.client.model.enums.TransType;
import com.yimayhd.pay.client.model.query.audit.AuditOrderQuery;
import com.yimayhd.pay.client.model.query.audit.AuditProgressQuery;
import com.yimayhd.pay.client.model.query.audit.AuditResultQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.audit.AuditResult;

/**
 * Created by hongfei.guo on 2015/11/18.
 */
public class AuditServiceImpl implements AuditService {
	private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
	
	@Autowired
    private AuditRepo auditRepo ;

	@Override
	public PageVO<PayAuditResultVO> queryAuditProgress(AuditQuery query) throws Exception {
		
		AuditProgressQuery queryDO = AuditQuery.getAuditProgressQuery(query);
		
		PayPageResultDTO<PayAuditResultDO> result = auditRepo.queryAuditProgress(queryDO);
		if(result == null){
			log.error("auditRepo.queryAuditProgress return value is null !returnValue : {}", JSON.toJSONString(result));
			return new PageVO<PayAuditResultVO>();
		}
		
		List<PayAuditResultVO> listVO = new ArrayList<PayAuditResultVO>();
		List<PayAuditResultDO> listDO = result.getList();
		if(!CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(PayAuditResultVO.getPayAuditResultVO(listDO.get(i)));
			}
		}
		return new PageVO<PayAuditResultVO>(1, 10, 10, listVO);
	}

	@Override
	public List<PayAuditResultVO> queryAuditResult(AuditQuery query) throws Exception {
		
		AuditResultQuery queryDO = AuditQuery.getAuditResultQuery(query);
		AuditResult result = auditRepo.queryAuditResult(queryDO);
		if(result == null){
			log.error("auditRepo.queryAuditResult return value is null !returnValue : {}", JSON.toJSONString(result));
			return new ArrayList<PayAuditResultVO>();
		}
		
		List<PayAuditResultVO> listVO = new ArrayList<PayAuditResultVO>();
		List<PayAuditResultDO> listDO = result.getAuditResultDOList();
		if(!CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(PayAuditResultVO.getPayAuditResultVO(listDO.get(i)));
			}
		}
		return listVO;
	}

	@Override
	public PageVO<PayAuditOrderVO> queryAuditOrder(AuditQuery query) throws Exception {
		
		AuditOrderQuery queryDO = AuditQuery.getAuditOrderQuery(query);
		PayPageResultDTO<PayAuditOrderDO> result = auditRepo.queryAuditOrder(queryDO);
		if(result == null){
			log.error("auditRepo.queryAuditOrder return value is null !returnValue : {}", JSON.toJSONString(result));
			return new PageVO<PayAuditOrderVO>();
		}
		
		List<PayAuditOrderVO> listVO = new ArrayList<PayAuditOrderVO>();
		List<PayAuditOrderDO> listDO = result.getList();
		if(!CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(PayAuditOrderVO.getPayAuditOrderVO(listDO.get(i)));
			}
		}
		return new PageVO<PayAuditOrderVO>(queryDO.getPageNo(), queryDO.getPageSize(), result.getTotalCount(), listVO);
	}

	@Override
	public void downloadAuditOrder(HttpServletResponse response, AuditQuery query) throws Exception {
		
		String fileName = query.getAuditTypeDesc() + query.getAuditDate() +"对账单.xls";
		
		String path = this.getClass().getClassLoader().getResource("").getPath() +"/"+ fileName;
		this.exportAuditOrderExcel(query, path);
		
		File file = new File(path);
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" +  new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        
        //删除生成的文件
        file.delete();
	}
	
	private void exportAuditOrderExcel(AuditQuery query, String path) throws Exception {
		
		WritableWorkbook wwb = Workbook.createWorkbook(new File(path));
		WritableSheet ws = wwb.createSheet("aaa", 0);
		Label label = new Label(0, 0, "交易流水号");
		ws.addCell(label); 
		label = new Label(1, 0, "交易类型");
		ws.addCell(label);
		label = new Label(2, 0, "平台交易时间");
		ws.addCell(label);
		label = new Label(3, 0, "平台交易金额（元）");
		ws.addCell(label);
		label = new Label(4, 0, "第三方交易时间");
		ws.addCell(label);
		label = new Label(5, 0, "第三方交易金额（元）");
		ws.addCell(label);
		label = new Label(6, 0, "对账状态");
		ws.addCell(label);
		label = new Label(7, 0, "备注");
		ws.addCell(label);
		
		query.setPageSize(Constant.DEFAULT_PAGE_MAX_SIZE);
		int pageNumber = 1;
		
		int rowNo = 1;
		int totalPage = 0;
		PageVO<PayAuditOrderVO> pageVO = null;
		do{
			query.setPageNumber(pageNumber);
			pageVO = this.queryAuditOrder(query);
			int totalCount = pageVO.getTotalCount();
			if(totalPage == 0){
				totalPage = totalCount / Constant.DEFAULT_PAGE_MAX_SIZE;
				int temp = totalCount % Constant.DEFAULT_PAGE_MAX_SIZE;
				if(temp > 0){
					totalPage += 1;
				}
			}
			
			List<PayAuditOrderVO> list = pageVO.getItemList();
			PayAuditOrderVO item = null;
			for(int i = 0; i < list.size(); i++){
				item = list.get(i);
				
				label = new Label(0, rowNo, item.getBizOrderId()+"");
				ws.addCell(label);
				TransType transType = TransType.getByType(item.getTransType());
				label = new Label(1, rowNo, transType != null ? transType.getDesc() : "");
				ws.addCell(label);
				label = new Label(2, rowNo, DateUtil.dateToString(item.getTradeDate(), DateUtil.DATE_TIME_FORMAT));
				ws.addCell(label);
				label = new Label(3, rowNo, item.getTradeAmountYuan()+"");
				ws.addCell(label);
				label = new Label(4, rowNo, DateUtil.dateToString(item.getOppositeTradeDate(), DateUtil.DATE_TIME_FORMAT));
				ws.addCell(label);
				label = new Label(5, rowNo, item.getOppositeTradeAmountYuan()+"");
				ws.addCell(label);
				label = new Label(6, rowNo, transOrderStatusDesc(item.getAuditOrderStatus()));
				ws.addCell(label);
				label = new Label(7, rowNo, "");
				ws.addCell(label);
				
				rowNo ++;
			}
			
			pageNumber ++;
		}while(pageNumber <= totalPage);
		
		wwb.write();
		wwb.close(); 
	}
	
	private String transOrderStatusDesc(int status){
		
		String desc = "";
		switch(status){
			case 1:
				desc = "金额不一致";
				break;
			case 2:
				desc = "状态不一致";
				break;
			case 3:
				desc = "他有我无";
				break;
			case 4:
				desc = "我有他无";
				break;
			case 5:
				desc = "对账相符 ";
				break;
			default:
				desc = "";
		}
		return desc;
	}
}
