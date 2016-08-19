package com.yimayhd.palace.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.param.item.PictureUpdateDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

/**
 * 资源接口
 * 
 * @author yebin
 *
 */
public class ResourceRepo {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	protected ItemQueryService itemQueryServiceRef;
	@Autowired
	protected ResourcePublishService resourcePublishServiceRef;

	public boolean addPictures(List<PicturesDO> list) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.addPictures", list);
		fillPictureDomain(list);
		
		ICResult<Boolean> icResultPic = resourcePublishServiceRef.addPictures(list);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.addPictures", icResultPic);
		return icResultPic.getModule();
	}

	public boolean updatePictures(PictureUpdateDTO pictureupdatedto) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.updatePictures", pictureupdatedto);
		if( pictureupdatedto != null ){
			List<PicturesDO> addPictureDOList = pictureupdatedto.getAddPictureDOList();
			fillPictureDomain(addPictureDOList) ;
//			pictureupdatedto.setAddPictureDOList(fillPictureDomain(addPictureDOList));
			List<PicturesDO> updatePictrueDOList = pictureupdatedto.getUpdatePictureDOList();
			fillPictureDomain(updatePictrueDOList) ;
//			pictureupdatedto.setUpdatePictureDOList(fillPictureDomain(updatePictrueDOList));
		}
		
		ICResult<Boolean> icResultPic = resourcePublishServiceRef.updatePictures(pictureupdatedto);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.updatePictures", icResultPic);
		return icResultPic.getModule();
	}
	
	private static void fillPictureDomain(List<PicturesDO> pictureDOList){
		if( !CollectionUtils.isEmpty(pictureDOList )){
			for( PicturesDO picturesDO : pictureDOList ){
				picturesDO.setDomain(Constant.DOMAIN_JIUXIU);
			}
		}
	}

}
