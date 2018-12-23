package com.jiahangchun.service.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.jiahangchun.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/12/21 下午4:41
 **/
@Service
@Slf4j
public class AliyunOssManager {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssS");

    private String env = "daily";
    @Value("${aliyun-oss.endpoint}")
    private String endpoint;
    @Value("${aliyun-oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun-oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun-oss.publicBucketName}")
    private String publicBucketName;
    @Value("${aliyun-oss.publicBucketDomain}")
    private String publicBucketDomain;

    private OSSClient ossClient;

    /**
     * 初始化变量并生成静态变量
     */
    @PostConstruct
    public void init() {
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(publicBucketName)) {
                log.info("Creating intranetOssClient bucket {}", publicBucketName);
                ossClient.createBucket(publicBucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(publicBucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);

            }

        } catch (Exception e) {
            log.error("error to create intranetOssClient", e);
        }


    }

    public String generatePostPolicy(Date expiration, PolicyConditions conds) {
        try {
            return ossClient.generatePostPolicy(expiration, conds);
        } catch (Exception e) {
            log.error("error to generatePostPolicy, expiration:{}, conds:{}",
                    new Object[]{expiration, JsonUtil.toJson(conds), e});
            return null;
        }

    }

    public String calculatePostSignature(String postPolicy) {
        try {
            return ossClient.calculatePostSignature(postPolicy);
        } catch (Exception e) {
            log.error("error to calculatePostSignature, postPolicy:{}", postPolicy, e);
            return null;
        }
    }

    public URL generatePresignedUrl(String bucketName, String key, Date expiration) {
        try {
            return ossClient.generatePresignedUrl(bucketName, key, expiration, HttpMethod.GET);
        } catch (Exception e) {
            log.error("error to generatePresignedUrl, bucketName:{}, key:{}", new Object[]{bucketName, key, e});
            return null;
        }
    }

    /**
     * 上传文件
     *
     * @param remoteName
     * @param file
     * @return
     */
    public String uploadFile(String remoteName, File file) {
        PutObjectResult result = ossClient.putObject(new PutObjectRequest(publicBucketName, remoteName, file));//new File(filePath)
        return remoteName;
    }

//    public String uploadPublicFile(String filePath, Long userId, String fileSuffixName, LinkedHashMap<String, String> meaningMap) {
//
//        try {
//            String remoteName = genPublicFileRemoteName(userId, fileSuffixName);
//            File file = new File(filePath);
//            //file.createNewFile();
//            file = CreateEmptyExcelUtil.writeFile(file,meaningMap);
//            PutObjectResult result = ossClient.putObject(new PutObjectRequest(publicBucketName, remoteName, file));//new File(filePath)
//            return remoteName;
//        } catch (Exception e) {
//            log.error("error to upload public file, filePath:{}, userId:{}", new Object[]{filePath, userId, e});
//            return null;
//        }
//    }

//    public String uploadPublicFile(byte[] fileData, Long userId, String fileSuffixName,LinkedHashMap<String, String> meaningMap) {
//        String localFilePath = saveFileToLocal(fileData);
//        if (StringUtils.isBlank(localFilePath)) {
//            return null;
//        }
//        return uploadPublicFile(localFilePath, userId, fileSuffixName,meaningMap);
//    }

    public String saveFileToLocal(byte[] fileData) {
        if (fileData == null) {
            throw new IllegalArgumentException("file data is null");
        }
        String tmpFolderPath = "/tmp";
        //文件临时存放目录准备
        File folder = new File(tmpFolderPath);
        if (folder.exists() == false) {
            folder.mkdir();
        } else if (folder.isDirectory() == false) {
            folder.delete();
            folder.mkdir();
        }

        String fileName = "" + System.currentTimeMillis();
        String filePath = tmpFolderPath + "/" + fileName;
        OutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
            os.write(fileData);
            return filePath;
        } catch (Exception e) {
            log.error("error to write file data on local storage", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception ignore) {

                }
            }
        }

        return null;
    }

    public String genPublicFileDownloadUrl(String remoteName) {
        return publicBucketDomain + "/" + remoteName;
    }

    /**
     * FIXME 文件命名规则先做简单实现
     *
     * @return
     */
    public String genPublicFileRemoteName(long userId, String fileSuffixName) {
        String fileName = "open_file" + StringUtils.rightPad(DATE_FORMAT.format(new Date()), 17, "0") + "0" + StringUtils.leftPad("" + userId % 10000, 4, "0")
                + genRandomNumber(3);
        if (StringUtils.isNotBlank(fileSuffixName)) {
            fileName = fileName + "." + fileSuffixName;
        }
        return fileName;
    }

    public static String genRandomNumber(int count) {
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            if (i == 0) {
                num = r.nextInt(9);
            }

            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getPublicBucketName() {
        return publicBucketName;
    }

    public void setPublicBucketName(String publicBucketName) {
        this.publicBucketName = publicBucketName;
    }

    public String getPublicBucketDomain() {
        return publicBucketDomain;
    }

    public void setPublicBucketDomain(String publicBucketDomain) {
        this.publicBucketDomain = publicBucketDomain;
    }


}
