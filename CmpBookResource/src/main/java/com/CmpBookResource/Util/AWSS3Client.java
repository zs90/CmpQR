package com.CmpBookResource.Util;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

@Service
public class AWSS3Client {
	private static AmazonS3 s3client;
	
	static{
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(20);
        
        s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.CN_NORTH_1)
                .withClientConfiguration(config)
                .build();
	}
	
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
	
	@PreDestroy
	public void destroy(){
		com.amazonaws.http.IdleConnectionReaper.shutdown();
	}
}
