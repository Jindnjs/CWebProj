package com.example.CWebProj.AwsBucket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	
	@Value("AKIA6GBMFUHVHYKMCMGL")
	private String awsAccessKey;
	
	@Value("EmgTMhJcPRxn1hPrP5NwIfJbC9wlfSJAvdBQt9qF")
	private String awsSecretKey;
	
	@Value("ap-northeast-2")
	private String region;
	
	@Bean
	public AmazonS3 s3client() {
		BasicAWSCredentials awsCreds =
				new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		return AmazonS3ClientBuilder.standard()
				.withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();
	}
}
