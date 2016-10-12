package com.yimayhd.palace.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.repo.user.UserRepo;
import com.yimayhd.palace.util.CommonUtil;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.palace.util.WebResourceConfigUtil;

/**
 * 
 * @author wzf
 *
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class) ;
	private static final String COLUMN_SEPARATOR = ",";
	private static final String ROW_SEPARATOR = "\n";
	private static final String TAB_SEPARATOR = "\r";

	@Autowired
	private UserRepo userRepo;
	
	@RequestMapping(value = "/mobile_userId", method = RequestMethod.GET)
	public String getUserId(Model model){
		String path = WebResourceConfigUtil.getRootPath();
		model.addAttribute("path", path);
		return "/system/user/mobileUserId";
	}
	
	/**
	 * 用户列表
	 * 
	 * @param model
	 * @param userListQuery
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMobilesByUserIds", method = RequestMethod.POST)
	public void getMobilesByUserIds(Model model, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> list = readFile(request) ;
		List<Long> ids = stringList2LongList(list);
		if( CollectionUtils.isEmpty(ids) ){
			return ;
		}
		int start = 0;
		int total = ids.size() ;
		int pageSize = 100 ;
		
		List<List<String>> result = new ArrayList<>() ;
		List<String> title = new ArrayList<>() ;
		title.add("userId");
		title.add("mobile");
		result.add(title);
		while( true ){
			int end = start + pageSize ;
			if( end >= total ){
				end = total;
			}
			List<Long> userIds = ids.subList(start, end) ;
			Map<Long, String> map = userRepo.getMobilesByUserIds(userIds);
			List<List<String>> rows = map2List(map);
			result.addAll(rows);
			if( end >= total ){
				break;
			}
			start = start + pageSize ;
		}
		
		String fileName = getFileName(request);
		write2Stream(response, result, fileName);
	}

	
	@RequestMapping(value = "/getUserIdsByUserMobiles", method = RequestMethod.POST)
	public void getUserIdsByUserMobiles(Model model, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> mobiles = readFile(request) ;
		if( CollectionUtils.isEmpty(mobiles) ){
			return ;
		}
		
		int start = 0;
		int total = mobiles.size() ;
		int pageSize = 100 ;
		
		List<List<String>> result = new ArrayList<>() ;
		List<String> title = new ArrayList<>() ;
		title.add("mobile");
		title.add("userId");
		result.add(title);
		while( true ){
			int end = start + pageSize ;
			if( end >= total ){
				end = total;
			}
			List<String> subMobiles = mobiles.subList(start, end) ;
			Map<String, Long> map = userRepo.getUserIdsByMobiles(subMobiles);
			List<List<String>> rows = map2List(map);
			if( !CollectionUtils.isEmpty(rows) ){
				result.addAll(rows);
			}
			if( end >= total ){
				break;
			}
			start = start + pageSize ;
		}
		
		String fileName = getFileName(request);
		write2Stream(response, result, fileName);
	}
	
	
	
	
	private List<String> readFile(MultipartHttpServletRequest request){
		List<String> values = new ArrayList<>() ;
		Iterator<String> iterator = request.getFileNames();
		while (iterator.hasNext()) {
			MultipartFile multipartFile = request.getFile(iterator.next());
			String fileName = multipartFile.getOriginalFilename().trim();
			if (!fileName.endsWith(".csv") ) {
				return null ;
			}
			try {
				byte[] bytes = multipartFile.getBytes() ;
				String content = new String(bytes, "UTF-8");
				String[] lines = content.split(ROW_SEPARATOR) ;
				for(String line : lines ){
					line = line.replace(TAB_SEPARATOR, "").replace(ROW_SEPARATOR, "");
					values.add(line);
				}
			} catch (IOException e) {
				logger.error("readFile failed!", e);
			}
			break ;
		}
		return values ;
	}
	private String getFileName(MultipartHttpServletRequest request){
		Iterator<String> iterator = request.getFileNames();
		while (iterator.hasNext()) {
			MultipartFile multipartFile = request.getFile(iterator.next());
			String fileName = multipartFile.getOriginalFilename().trim();
			return fileName ;
		}
		return null ;
	}
	
	private List<Long> stringList2LongList(List<String> list){
		if( CollectionUtils.isEmpty(list) ){
			return null;
		}
		List<Long> ids = new ArrayList<>();
		for( String value : list ){
			if( StringUtils.isNotBlank(value) ){
				String val = value.replace(TAB_SEPARATOR, "").replace(ROW_SEPARATOR, "");
				if(StringUtils.isNumeric(val) ){
					long id = Long.parseLong(val);
					ids.add(id);
				}
			}
		}
		return ids ;
		
	}
	
	private <K, V> List<List<String>> map2List(Map<K, V> map){
		if( CollectionUtils.isEmpty(map) ){
			return null;
		}
		List<List<String>> lines = new ArrayList<List<String>>() ;
		Set<K> keys = map.keySet() ;	
		for(K k : keys ){
			List<String> line = new ArrayList<>() ;
			V v = map.get(k);
			line.add(String.valueOf(k));
			line.add(String.valueOf(v)) ;
			lines.add(line);
		}
		return lines;
	}
	
	private String list2String(List<List<String>> lines){
		if( CollectionUtils.isEmpty(lines) ){
			return null;
		}
		StringBuilder sb = new StringBuilder() ;
		for(List<String> cols : lines ){
			int size = cols.size() ;
			for( int i= 0 ; i<size ; i++){
				String column = cols.get(i);
				sb.append(column) ;
				if( i != size - 1 ){
					sb.append(COLUMN_SEPARATOR) ;
				}
			}
			sb.append(ROW_SEPARATOR) ;
		}
		return sb.toString() ;
	}
	
	
	
	private void write2Stream(HttpServletResponse response, List<List<String>> rows, String fileName){
		String result = list2String(rows);
		OutputStream toClient = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
			String datetime = format.format(new Date()) ;
			String pref = fileName.substring(0, fileName.lastIndexOf(".") );
			String subfix = fileName.substring(fileName.lastIndexOf(".") );
            fileName = pref+"["+datetime+"]"+subfix ;
            // 以流的形式下载文件。
            byte[] buffer = result.getBytes();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
            response.addHeader("Content-Length", "" + buffer.length);
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            
        } catch (Exception e) {
            log.error("write file failed!",e);
        }finally{
        	if( toClient != null ){
        		try {
        			toClient.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        }
	}
}
