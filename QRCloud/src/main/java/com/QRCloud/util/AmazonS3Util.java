package com.QRCloud.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

public class AmazonS3Util {
	private AmazonS3 s3 = null;
	private TransferManager tx;
	
	private void AmazonS3Init(){
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(20);
        
        s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.CN_NORTH_1)
                .withClientConfiguration(config)
                .build();
        
        tx = TransferManagerBuilder.standard().withS3Client(s3).build();
	}
	
	public AmazonS3 getS3Client(){
		return s3;
	}
	
	public TransferManager getS3Tranfer(){
		return tx;
	}
	
	public void destroy(){
		com.amazonaws.http.IdleConnectionReaper.shutdown();
	}
}
