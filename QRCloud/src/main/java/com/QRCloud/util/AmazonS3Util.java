package com.QRCloud.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

/**
 * s3工具类，负责连接管理。 需要注意的是，s3客户端是线程安全的，可以直接将其作为一个singleton对象注入到spring框架中。
 * 
 * @author Shane
 * @version 1.0
 */
public class AmazonS3Util {
	private AmazonS3 s3 = null;
	private TransferManager tx;

	/**
	 * 初始化s3客户端和上传所需要的管理类对象tx
	 */
	private void AmazonS3Init() {
		ClientConfiguration config = new ClientConfiguration();
		config.setMaxConnections(20);

		s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.CN_NORTH_1).withClientConfiguration(config).build();

		tx = TransferManagerBuilder.standard().withS3Client(s3).build();
	}

	/**
	 * 获取s3对象
	 * 
	 * @return s3对象
	 */
	public AmazonS3 getS3Client() {
		return s3;
	}

	/**
	 * 获取tx对象
	 * 
	 * @return tx对象
	 */
	public TransferManager getS3Tranfer() {
		return tx;
	}

	/**
	 * 系统关闭时，需要手动释放掉s3相关的连接资源，可由spring框架调用
	 */
	public void destroy() {
		com.amazonaws.http.IdleConnectionReaper.shutdown();
	}
}
