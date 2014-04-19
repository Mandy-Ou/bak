package com.cmw.service.impl.sys;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.IconDaoInter;
import com.cmw.entity.sys.IconEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.IconService;


/**
 * 系统图标  Service实现类
 * @author 程明卫
 * @date 2013-08-24T00:00:00
 */
@Description(remark="系统图标业务实现类",createDate="2013-08-24T00:00:00",author="程明卫")
@Service("iconService")
public class IconServiceImpl extends AbsService<IconEntity, Long> implements  IconService {
	@Autowired
	private IconDaoInter iconDao;
	//批处理文件名
	private static final String BAT_FILENAME = "autoFileInfos.bat";
	//存放标准48*48图标文件信息的Txt 文件名
	private static final String BIGIMGINFOS = "bigImgInfos.txt";
	//存扁平图标文件信息的Txt 文件名
	private static final String FLAT_ICONS = "flatImgInfos.txt";
	//存放小标文件信息的Txt 文件名	
	private static final String SMALL_ICONS = "smallImgInfos.txt";
	
	@Override
	public GenericDaoInter<IconEntity, Long> getDao() {
		return iconDao;
	}
	
	@Override
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		runbat(complexData);
		saveDatas(complexData);
	}

	
	private void saveDatas(Map<String, Object> complexData) throws ServiceException{
		String fileServerPath = (String)complexData.get("fileServerPath");
		List<IconEntity> iconList = new ArrayList<IconEntity>();
		List<IconEntity> tempList = null;
		List<String> fileInfoList = null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("iconType", BussStateConstant.ICON_ICONTYPE_1);
		List<IconEntity> bigImgList = getEntityList(map);
		fileInfoList = getFileInfos(fileServerPath, BIGIMGINFOS);
		if(null == bigImgList || bigImgList.size() == 0){
			tempList = createIconEntitys(complexData, BussStateConstant.ICON_ICONTYPE_1, fileInfoList);
		}else{
			tempList = createIconEntitys(complexData, BussStateConstant.ICON_ICONTYPE_1, fileInfoList, bigImgList);
		}
		if(null != tempList && tempList.size() > 0) iconList.addAll(tempList);
		
		map.put("iconType", BussStateConstant.ICON_ICONTYPE_2);
		List<IconEntity> flatImgList = getEntityList(map);
		fileInfoList = getFileInfos(fileServerPath, FLAT_ICONS);
		if(null == flatImgList || flatImgList.size() == 0){
			tempList = createIconEntitys(complexData, BussStateConstant.ICON_ICONTYPE_2, fileInfoList);
		}else{
			tempList = createIconEntitys(complexData, BussStateConstant.ICON_ICONTYPE_1, fileInfoList, flatImgList);
		}
		if(null != tempList && tempList.size() > 0) iconList.addAll(tempList);
		
		map.put("iconType", BussStateConstant.ICON_ICONTYPE_3);
		List<IconEntity> smallImgList = getEntityList(map);
		fileInfoList = getFileInfos(fileServerPath, SMALL_ICONS);
		if(null == smallImgList || smallImgList.size() == 0){
			tempList = createIconEntitys(complexData, BussStateConstant.ICON_ICONTYPE_3, fileInfoList);
		}else{
			tempList = createIconEntitys(complexData, BussStateConstant.ICON_ICONTYPE_1, fileInfoList, smallImgList);
		}
		if(null != tempList && tempList.size() > 0) iconList.addAll(tempList);
		
		if(null != iconList && iconList.size()>0) batchSaveEntitys(iconList);
	}
	
	private List<String> getFileInfos(String fileServerPath, String fileName){
		String absFilePath = fileServerPath + fileName;
		String fileInfosStr = FileUtil.ReadFileToStr(absFilePath);
		fileInfosStr = StringHandler.getSpeatorPath(fileInfosStr);
		String[] fileInfoArr = fileInfosStr.split("\n");
		List<String> fileList = new ArrayList<String>();
		for(String fileInfo : fileInfoArr){
			if(!isImgExt(fileInfo)) continue;
			fileList.add(fileInfo);
		}
		return fileList;
	}
	
	private List<IconEntity> createIconEntitys(Map<String, Object> complexData,Integer iconType,List<String> fileInfos,List<IconEntity> iconList) throws ServiceException{
		/*------ step 1 : 如果硬盘上不存在文件删除图标表中指定类型的图标数据 -------*/
		if(null == fileInfos || fileInfos.size() == 0){/*如果没有文件，测清空旧图标数据*/
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("iconType", iconType);
			deleteEntitys(map);
			return null;
		}
		
		/*------ step 2 : 找出数据库图标表存在而硬盘上不存在的文件，并删除此类文件 -------*/
		List<Long> idsList = new ArrayList<Long>();
		for(IconEntity iconEntity : iconList){
			Long id = iconEntity.getId();
			String fileName = iconEntity.getFileName();
			String modifyTime = DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, iconEntity.getLastmod());
			boolean exist = false;
			for(String filePath : fileInfos){
				File file = new File(filePath);
				String _fileName = file.getName();
				String _modifyTime =  DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, new Date(file.lastModified()));
				if(fileName.equals(_fileName) && modifyTime.equals(_modifyTime)){
					exist = true;
					break;
				}
			}
			if(!exist) idsList.add(id);
		}
		if(null != idsList && idsList.size() > 0){
			Long[] ids = new Long[1];
			ids = idsList.toArray(ids);
			deleteEntitys(ids);
		}
		
		/*------ step 3 : 找出硬盘上存在则图标表中不存的新文件 -------*/
		List<String> newFileInfoList = new ArrayList<String>();
		for(String filePath : fileInfos){
			File file = new File(filePath);
			String _fileName = file.getName();
			String _modifyTime =  DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, new Date(file.lastModified()));
			boolean exist = false;
			for(IconEntity iconEntity : iconList){
				String fileName = iconEntity.getFileName();
				String modifyTime = DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, iconEntity.getLastmod());
				if(fileName.equals(_fileName) && modifyTime.equals(_modifyTime)){
					exist = true;
					break;
				}
			}
			if(!exist) newFileInfoList.add(filePath);
		}
		
		
		if(null != newFileInfoList && newFileInfoList.size() > 0){
			return createIconEntitys(complexData, iconType, newFileInfoList);
		}
		return null;
	}
	
	private List<IconEntity> createIconEntitys(Map<String, Object> complexData,Integer iconType,List<String> fileInfos){
		List<IconEntity> entitys = new ArrayList<IconEntity>();
		if(null == fileInfos || fileInfos.size() == 0) return null;
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		for(String fileInfo : fileInfos){
			File file = new File(fileInfo);
			IconEntity entity = createIconEntity(file, user, iconType);
			entitys.add(entity);
		}
		return entitys;
	}
	
	private IconEntity createIconEntity(File file,UserEntity user,Integer iconType){
		String fileName = file.getName();
		long fileSize = file.length();
		long modified = file.lastModified();
		String filePath = file.getAbsolutePath();
		filePath = StringHandler.getSpeatorPath(filePath);
		int index = filePath.indexOf("images");
		filePath = (filePath.substring(index));
		
		IconEntity entity = new IconEntity();
		entity.setFileName(fileName);
		entity.setFilePath(filePath);
		entity.setFileSize(fileSize);
		entity.setLastmod(new Date(modified));
		entity.setIconType(iconType);
		BeanUtil.setCreateInfo(user, entity);
		return entity;
	}
	/**
	 * 判断是否是指定的图片格式
	 * @param fileInfo	文件名
	 * @return
	 */
	private boolean isImgExt(String fileInfo){
		return (fileInfo.endsWith(".png") || fileInfo.endsWith(".PNG")
				|| fileInfo.endsWith(".jpg") || fileInfo.endsWith(".JPG")
				|| fileInfo.endsWith(".jpeg") || fileInfo.endsWith(".JPEG")
				|| fileInfo.endsWith(".ico") || fileInfo.endsWith(".ICO")
				|| fileInfo.endsWith(".bmp") || fileInfo.endsWith(".BMP"));
	}
	
	private void runbat(Map<String, Object> complexData) throws ServiceException{
		String fileServerPath = (String)complexData.get("fileServerPath");
		String absBatFile = fileServerPath + BAT_FILENAME;
		if(!FileUtil.exist(absBatFile)){
			creatBatFile(fileServerPath);
		}
		
		/*--执行批处理文件创建存放文件信息的txt文件--*/
	    try {
            Process ps = Runtime.getRuntime().exec("cmd /c start "+absBatFile);
            System.out.println(ps.getInputStream());
        } catch(IOException ioe) {
            ioe.printStackTrace();
            throw new ServiceException("exe ["+absBatFile+"] bat file failure!");
        }
	}
	
	
	private synchronized void creatBatFile(String fileServerPath){
		StringBuilder sbBat = new StringBuilder();
		String diverDir = fileServerPath.substring(0, 2);
		sbBat.append(diverDir+"\n");
		sbBat.append("cd "+fileServerPath+"\n");
		sbBat.append("cd big_icons\n")
		.append("dir/s/b >../"+BIGIMGINFOS+"\n")
		.append("cd ../flat_icons\n")
		.append("dir/s/b >../"+FLAT_ICONS+"\n")
		.append("cd ../small_icons\n")
		.append("dir/s/b >../"+SMALL_ICONS+"\n")
		.append("pause");
		String absFileName = fileServerPath + BAT_FILENAME;
		FileUtil.writeStrToFile(absFileName, sbBat.toString());
	}
}
