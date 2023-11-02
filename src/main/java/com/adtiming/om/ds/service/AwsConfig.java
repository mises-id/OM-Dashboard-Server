package com.adtiming.om.ds.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * ClassName AwsConfig
 *
 * @author wang
 * Date 2022/6/23 10:19
 */
@Configuration
@Component
public class AwsConfig {

    @Value("${amazon.aws.access-key-id}")
    public String accessKey;
    @Value("${amazon.aws.access-key-secret}")
    public String secretKey;
    @Value("${amazon.s3.region}")
    public String region;

    @Value("${amazon.s3.default-bucket}")
    public String bucket;
    @Value("${amazon.s3.endpoint}")
    public String endpoint;

    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    }


}