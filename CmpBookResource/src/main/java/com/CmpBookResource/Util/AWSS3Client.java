package com.CmpBookResource.Util;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

/**
 * s3工具类，负责管理s3相关客户端以及相关操作。这里也标记为service是为了利用spring的bean特性
 * @author Shane
 * @version 1.0
 */
@Service
public class AWSS3Client {
	private static AmazonS3 s3client;
	
	/**
	 * 静态语句块，用于在类初始化时，只初始化一次s3的client
	 */
	static{
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(20);
        
        s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.CN_NORTH_1)
                .withClientConfiguration(config)
                .build();
	}
	
	/**
	 * 获取加密签名的url。处理后的url将拥有20min的有效期，之后将不可再访问。
	 * @param bucketName 桶名
	 * @param objectKey 对象名
	 * @return 加密后的url
	 */
	public String getUrl(String bucketName, String objectKey){
		java.util.Date expiration = new java.util.Date();
		long msec = expiration.getTime();
		msec += 1000 * 20 * 60; // 20 minutes
		expiration.setTime(msec);
		             
		GeneratePresignedUrlRequest generatePresignedUrlRequest = 
		              new GeneratePresignedUrlRequest(bucketName, objectKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);
		             
		return s3client.generatePresignedUrl(generatePresignedUrlRequest).toString(); 
	}
	
	/**
	 * bean销毁前，手动关闭连接资源。
	 */
	@PreDestroy
	public void destroy(){
		com.amazonaws.http.IdleConnectionReaper.shutdown();
	}
}
