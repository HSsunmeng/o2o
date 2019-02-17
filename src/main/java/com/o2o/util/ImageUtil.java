package com.o2o.util;

import com.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
/**
 * 处理详情页图片
 * */
public class ImageUtil {
    private  static String basePath=Thread.currentThread().getContextClassLoader().getResource("gouwuzhu.jpg").getPath();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT=new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random RANDOM=new Random();
    private static Logger logger= LoggerFactory.getLogger(ImgUtil.class);
    public static String generateThumbnails(ImageHolder thumbnail, String targetAddr){
        String realName=getRandomName();
        String extension=getExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String realtiveAddr=targetAddr+realName+extension;
        logger.debug("current realtiveAddr is"+realtiveAddr);
        File dest=new File(PathUtil.getImgBasePath()+realtiveAddr);
        logger.debug("current complete add is"+PathUtil.getImgBasePath()+realtiveAddr);
        try{
            Thumbnails.of(thumbnail.getImage()).size(300,500)
                    .watermark(Positions.BOTTOM_CENTER,ImageIO
                            .read(new File(basePath)),0.5f)
                    . outputQuality(0.9f).toFile(dest);
        }catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }
        return realtiveAddr;
    }
    /**
     * 将CommonsMultipartFile转换成File类
     * */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile commonsMultipartFile){
        File file=new File(commonsMultipartFile.getOriginalFilename());
        try {
            commonsMultipartFile.transferTo(file);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return file;
    }
    /**
     * 创建目标文件所涉及到的目录
     * */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath=new File(realFileParentPath);
        if (!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    /**
     * @return 获取当前文件的扩展名如（jpg等）
     * */
    private static String getExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * @return 生成文件随机名，当前年月日小时分钟秒，加五位随机数
     * */
    public static String getRandomName() {
        int rannum=RANDOM.nextInt(89999)+10000;
        String nowTimeStr=SIMPLE_DATE_FORMAT.format(new Date());
        return nowTimeStr+rannum;
    }


    /**
     * 删除图片文件
     * */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath=new File(PathUtil.getImgBasePath()+storePath);
        if (fileOrPath.exists()){
            if (fileOrPath.isDirectory()){
                File files[]=fileOrPath.listFiles();
                for (int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
