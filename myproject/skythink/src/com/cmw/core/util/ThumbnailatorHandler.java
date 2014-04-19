package com.cmw.core.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnailator;

import com.cmw.core.base.exception.UtilException;


/**
 * 縮略图处理类
 * @author Administrator
 *
 */
public class ThumbnailatorHandler {
	
	/**
	 * 生成图像缩略图
	 * @param imageFileName 要生成缩略图的源文件
	 * @param thumImagesDirsPath	缩略图文件存放目录
	 * @param endFileName	缩略图文件名后缀
	 * @param width	宽度
	 * @param height	高度
	 * @throws UtilException
	 */
	@SuppressWarnings("unused")
	public static void makeThumImage(String imageFileName,String thumImagesDirsPath,String endFileName,int width,int height) throws UtilException{
		File imgFile = new File(imageFileName);
		if(null == imgFile) throw new UtilException("文件路径：【"+imageFileName+"】 不存在");
		if(!imgFile.isFile()) throw new UtilException("【"+imageFileName+"】 不是一个图片文件");
		String fileName = imgFile.getName();
		int offSet = fileName.lastIndexOf(".");
		String prefix = fileName.substring(offSet);
		String newFileName = fileName.substring(0,offSet)+endFileName+prefix;
		File newFile = new File(thumImagesDirsPath+newFileName);
		try {
			Thumbnailator.createThumbnail(imgFile, newFile, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将指定目录里的图片批量生成缩略图并存放到指定的文件夹<br/>
	 * 所有缩略图文件名格式：<br/>
	 *    原图文件名加+endPrefix.xx 
	 *    例如：原文件名 "user.png" --> "user_small.png"
	 * @param dirs	图片目录
	 * @param thumImagesDirs 缩略图存放目录
	 * @param endFileName	缩略图
	 * @throws UtilException 	
	 */
	public static void batchThumImages(String dirsPath,String thumImagesDirsPath,String endFileName,int width,int height) throws UtilException{
		File dirs = new File(dirsPath);
		if(null == dirs) throw new UtilException("文件路径：【"+dirsPath+"】 不存在");
		if(!dirs.isDirectory()) throw new UtilException("【"+dirsPath+"】 不是一个文件夹");
		File[] imagesFiles = dirs.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				System.out.println(name);
				return (name.endsWith(".png") || name.endsWith(".jpg")
						|| name.endsWith(".jpeg") || name.endsWith(".ico")
						|| name.endsWith(".bmp"));
			}
		});
		for(File imagesFile : imagesFiles){
			String fileName = imagesFile.getName();
			int offSet = fileName.lastIndexOf(".");
			String prefix = fileName.substring(offSet);
			String newFileName = fileName.substring(0,offSet)+endFileName+prefix;
			File newFile = new File(thumImagesDirsPath+newFileName);
			try {
				Thumbnailator.createThumbnail(imagesFile, newFile, width, height);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String thumImagesDirsPath = "F:/dev/smartplatform/WebContent/images/templates/listview/thumbnails/";
		String endFileName = "_small";
		int width = 180;
		int height=200;
		//**--------------- makeThumImage 生成单张缩略图片用法 CODE START -------------**//
//		String sourceFileName = "F:/dev/smartplatform/WebContent/images/templates/列表视图模板/artwork/ListView.png";
	
//		try {
//			makeThumImage(sourceFileName,thumImagesDirsPath,endFileName,width,height);
//		} catch (UtilException e) {
//			e.printStackTrace();
//		}
		//**--------------- makeThumImage 生成单张缩略图片用法 CODE END -------------**//
		
		//**--------------- batchThumImages 批量生成图片用法 CODE START -------------**//
		String dirsPath = "F:/dev/smartplatform/WebContent/images/templates/listview/artwork/";
		try {
			batchThumImages(dirsPath,thumImagesDirsPath,endFileName,width,height);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		//**--------------- batchThumImages 批量生成图片用法 CODE END -------------**//
	}

}
