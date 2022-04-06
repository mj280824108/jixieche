package com.weiwei.jixieche.controller;

import com.weiwei.jixieche.constant.ErrorCodeConstants;
import com.weiwei.jixieche.response.ResponseMessage;
import com.weiwei.jixieche.response.ResponseMessageFactory;
import com.weiwei.jixieche.utils.AliyunOSSUtils;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @ClassName 类名
 * @Description TODO
 * @Author houji
 * @Date 2019/8/13 18:08
 * @Version 2.0
 **/
@Api(tags="公共模块--OSS上传API")
@RestController
@RequestMapping("oss")
public class OssUploadController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TO_PATH="upLoad";
    private static final String RETURN_PATH="success";

    @Resource
    private AliyunOSSUtils aliyunOSSUtils;

    @PostMapping(value = "/toUpLoadFile")
    public String toUpLoadFile(){
        return TO_PATH;
    }

    /**
     * houji
     * 阿里的oss的图片上传
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    public ResponseMessage upload(@RequestPart(value = "file", required = false) MultipartFile file) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        logger.info("文件上传");
        String filename = sdf.format(new Date())+(int)(Math.random() * 1000000)+"."+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        try {
            if (file!=null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    result.setData(aliyunOSSUtils.upLoad(newFile));
                    //上传完成以后删除图片
                    newFile.delete();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setCode(ErrorCodeConstants.UPLOAD_FILE_ERORR.getId());
            result.setMessage(ErrorCodeConstants.UPLOAD_FILE_ERORR.getDescript());
        }
        return result;
    }

    /**
     * houji
     * 阿里的oss的图片上传
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadBatch")
    public ResponseMessage uploadBatch(@RequestPart(value = "file", required = false) MultipartFile[] file) {
        ResponseMessage result = ResponseMessageFactory.newInstance();
        List<String> imgList = new ArrayList<>();
        if(file != null && file.length > 0){
            for (int i =0;i<file.length;i++){
                String filename = file[i].getOriginalFilename();
                logger.info("文件上传"+filename);
                try {
                    if (file[i]!=null) {
                        if (!"".equals(filename.trim())) {
                            File newFile = new File(filename);
                            FileOutputStream os = new FileOutputStream(newFile);
                            os.write(file[i].getBytes());
                            os.close();
                            file[i].transferTo(newFile);
                            // 上传到OSS
                            //result.setData(aliyunOSSUtils.upLoad(newFile));
                            imgList.add(aliyunOSSUtils.upLoad(newFile));
                            //上传完成以后删除图片
                            newFile.delete();
                        }
                    }
                } catch (Exception ex) {
                    logger.info("文件上传异常");
                    break;
                }
            }
            result.setData(imgList);
        }else{
            result.setCode(ErrorCodeConstants.UPLOAD_FILE_ERORR.getId());
            result.setMessage(ErrorCodeConstants.UPLOAD_FILE_ERORR.getDescript());
        }
        return result;
    }

}
