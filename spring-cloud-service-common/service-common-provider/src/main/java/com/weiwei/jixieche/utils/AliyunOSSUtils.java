package com.weiwei.jixieche.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.weiwei.jixieche.config.UploadConfig;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;
import java.util.UUID;

/**
 * @ClassName AliyunOSSUtil
 * @Description TODO
 * @Author houji
 * @Date 2019/5/13 13:49
 * @Version 2.0
 **/
@Component
public class AliyunOSSUtils {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AliyunOSSUtils.class);

    @Resource
    private UploadConfig uploadConfig;

    /** 上传文件*/
    public String upLoad(File file) {
        String ossUploadUrl = null;
        String endpoint=uploadConfig.getOSS_END_POINT();
        String accessKeyId=uploadConfig.getOSS_ACCESS_KEY_ID();
        String accessKeySecret=uploadConfig.getOSS_ACCESS_KEY_SECRET();
        String bucketName=uploadConfig.getOSS_BUCKET_NAME1();
        String fileHost=uploadConfig.getOSS_FILE_HOST();

        // 判断文件
        if(file==null){
            return null;
        }
        OSSClient client=new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断容器是否存在,不存在就创建
            if (!client.doesBucketExist(bucketName)) {
                client.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                client.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = fileHost + "/" +file.getName();
            // 上传文件
            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            // 设置权限(公开读)
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            if (result != null) {
                ossUploadUrl = endpoint +"/"+ fileUrl;
                logger.info("------OSS文件上传成功------" + ossUploadUrl);
                return ossUploadUrl;
            }
        }catch (OSSException oe){
            logger.error(oe.getMessage());
        }catch (ClientException ce){
            logger.error(ce.getErrorMessage());
        }finally{
            if(client!=null){
                client.shutdown();
            }
        }
        return ossUploadUrl;
    }

    public String doUpload(String suffix, String fileStr) {
        String ossUploadUrl = null;
        String endpoint=uploadConfig.getOSS_END_POINT();
        String accessKeyId=uploadConfig.getOSS_ACCESS_KEY_ID();
        String accessKeySecret=uploadConfig.getOSS_ACCESS_KEY_SECRET();
        String bucketName=uploadConfig.getOSS_BUCKET_NAME1();
        String fileHost=uploadConfig.getOSS_FILE_HOST();

        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        if (!ossClient.doesBucketExist(bucketName)) {
            createBucket(ossClient, bucketName);
        }

        byte[] fileByte = Base64.getDecoder().decode(fileStr);
        String filename = createFilename(fileHost, suffix);

        PutObjectResult result = ossClient.putObject(bucketName, filename, new ByteArrayInputStream(fileByte));
        ossClient.shutdown();

        if (result == null) {
            return null;
        }

        return endpoint + "/" + filename;
    }

    private void createBucket(OSSClient ossClient, String bucketName) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
        ossClient.createBucket(createBucketRequest);
    }

    private String createFilename(String filePath, String suffix) {
        return filePath + "/" + UUID.randomUUID().toString().replace("-", "") + "." + suffix;
    }

}
