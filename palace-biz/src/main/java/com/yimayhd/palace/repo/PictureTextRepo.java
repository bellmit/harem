package com.yimayhd.palace.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComentDO;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.dto.ComentQueryDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commentcenter.client.service.ComPictureTextService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.util.RepoUtils;

/**
 * 标签Repo
 * 
 * @author yebin
 *
 */
public class PictureTextRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ComPictureTextService comPictureTextService;

	public PicTextResult getPictureText(long outId, PictureText outType) {
		if (outId <= 0 || outType == null) {
			log.warn("PictureTextRepo.getPictureText(outId,outType) warn: 参数异常");
			log.warn("Param outId=" + outId);
			log.warn("Param outType=" + outType);
			throw new BaseException("参数异常");
		}
		ComentQueryDTO comentQueryDTO = new ComentQueryDTO();
		comentQueryDTO.setOutId(outId);
		comentQueryDTO.setDomain(Constant.DOMAIN_JIUXIU);
		comentQueryDTO.setOutType(outType.name());
		RepoUtils.requestLog(log, "comPictureTextService.getPictureText", comentQueryDTO);
		BaseResult<PicTextResult> pictureText = comPictureTextService.getPictureText(comentQueryDTO);
//		RepoUtils.resultLog(log, "comPictureTextService.getPictureText", pictureText);
		return pictureText.getValue();
	}
	@Deprecated
	public void savePictureText(ComentDTO comentDTO) {
		if (comentDTO == null) {
			log.warn("PictureTextRepo.savePictureText(comentDTO) warn: 参数异常");
			log.warn("Param comentDTO=" + comentDTO);
			throw new BaseException("参数异常");
		}
		comentDTO.setDomain(Constant.DOMAIN_JIUXIU);
		RepoUtils.requestLog(log, "comPictureTextService.savePictureText", comentDTO);
		BaseResult<ComentDO> savePictureText = comPictureTextService.savePictureText(comentDTO);
		RepoUtils.resultLog(log, "comPictureTextService.savePictureText", savePictureText);
	}

	@Deprecated
	public void updatePictureText(ComentEditDTO comentEditDTO) {
		if (comentEditDTO == null) {
			log.warn("PictureTextRepo.updatePictureText(comentEditDTO) warn: 参数异常");
			log.warn("Param comentEditDTO=" + comentEditDTO);
			throw new BaseException("参数异常");
		}
		RepoUtils.requestLog(log, "comPictureTextService.updatePictureText", comentEditDTO);
		BaseResult<ComentDO> updatePictureText = comPictureTextService.updatePictureText(comentEditDTO);
		RepoUtils.resultLog(log, "comPictureTextService.updatePictureText", updatePictureText);
	}
	//TODO ComentEditDTO 中的itemType
	public void editPictureText(ComentEditDTO comentEditDTO) {
		if (comentEditDTO == null) {
			log.warn("PictureTextRepo.editPictureText(comentEditDTO) warn: 参数异常");
			log.warn("Param comentEditDTO=" + comentEditDTO);
			throw new BaseException("参数异常");
		}
		comentEditDTO.setDomain(Constant.DOMAIN_JIUXIU);
		RepoUtils.requestLog(log, "comPictureTextService.editPictureText", comentEditDTO);
		BaseResult<ComentDO> editPictureText = comPictureTextService.editPictureText(comentEditDTO);
		RepoUtils.resultLog(log, "comPictureTextService.editPictureText", editPictureText);
	}
}
