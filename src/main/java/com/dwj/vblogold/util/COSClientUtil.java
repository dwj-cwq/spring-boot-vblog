package com.dwj.vblogold.util;

import com.dwj.vblogold.constants.OSSClientConstants;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public class COSClientUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static String ACCESS_KEY_ID;

    private static String ACCESS_KEY_SECRET;

    private static String BUCKET_NAME;

    private static String REGION;

    //初始化属性
    static {
        REGION = OSSClientConstants.REGION;
        ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
        BUCKET_NAME = OSSClientConstants.BACKET_NAME;
    }

    private static COSClient cosClient = null;

    /**
     * 获得阿里云OSS客户端对象
     *
     * @return ossClient
     */
    public static void initOSSClient() {
        COSCredentials cred = new BasicCOSCredentials(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    /**
     * 创建存储空间
     *
     * @return 桶名
     */
    public static String createBucketName() {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(BUCKET_NAME);
        // 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try {
            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
            return bucketResult.getName();
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
            return null;
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
            return null;
        }
    }

    /**
     * 删除存储空间buckName
     */
    public static void deleteBucket() {
        cosClient.deleteBucket(BUCKET_NAME);
        logger.info("删除" + BUCKET_NAME + "Bucket成功");
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param cosClient oss连接
     * @param key       Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(COSClient cosClient, String key) {
        cosClient.deleteObject(BUCKET_NAME, key);
        logger.info("删除" + BUCKET_NAME + "下的文件" + key + "成功");
    }

    /**
     * 上传图片至OSS
     *
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @return String 返回的唯一MD5数字签名
     */
    public static String uploadObject2OSS(File file) {
        // 指定要上传到 COS 上对象键
        String key = "vblog/image/" + file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, file);
        cosClient.putObject(putObjectRequest);
        return BUCKET_NAME + "." + "cos" + "." + REGION + ".myqcloud.com." + key;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

    public static void closedCOSClient() {
        cosClient.shutdown();
    }
}
