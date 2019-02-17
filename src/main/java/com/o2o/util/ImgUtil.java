package com.o2o.util;

import com.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImgUtil {
    private  static String basePath=Thread.currentThread().getContextClassLoader().getResource("gouwuzhu.jpg").getPath();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT=new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random RANDOM=new Random();
    private static Logger logger= LoggerFactory.getLogger(ImgUtil.class);
    /**
     * 处理缩略图，并返回新生成图片的相对路径
     * */
    public static String generateThumbnails(ImageHolder thumbnail, String targetAddr){
        //获取不重复的随机名
        String realName=getRandomName();
        //获取文件的扩展名（JPG等）
        String extension=getExtension(thumbnail.getImageName());
        //如果目标文件不存在就自动创建
        makeDirPath(targetAddr);
        //获取文件储存的相对路径
        String realtiveAddr=targetAddr+realName+extension;
        logger.debug("current realtiveAddr is"+realtiveAddr);
        //获取文件要保存的目标路径
        File dest=new File(PathUtil.getImgBasePath()+realtiveAddr);
        logger.debug("current complete add is"+PathUtil.getImgBasePath()+realtiveAddr);
        //调用Thumbnails生成图片水印
        try{
            Thumbnails.of(thumbnail.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_CENTER,ImageIO
                            .read(new File(basePath)),0.5f)
                    . outputQuality(0.8f).toFile(dest);
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

    public static void main(String[] args) throws IOException {

        Thumbnails.of(new File("F:/image/xiaozhu.jpg")).size(200,200).watermark(Positions.BOTTOM_CENTER,
                ImageIO.read (new File(basePath+"/gouwuzhu.jpg")),0.5f). outputQuality(0.8f).toFile("F:/image/xiaozhuNew.jpg");
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
