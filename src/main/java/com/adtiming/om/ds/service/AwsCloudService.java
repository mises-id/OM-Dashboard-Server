// Copyright 2021 ADTIMING TECHNOLOGY COMPANY LIMITED
// Licensed under the GNU Lesser General Public License Version 3

package com.adtiming.om.ds.service;

import com.alibaba.fastjson.JSON;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EC2ContainerCredentialsProviderWrapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.io.File;

/**
 * Used to upload files to AWS s3
 */
@Service
public class AwsCloudService extends BaseService  {

    private static final Logger LOG = LogManager.getLogger();

    private AmazonS3 client;
    @Resource
    private AwsConfig cfg;



    public void putObject(String key, File file) {
        try {
            if (client == null) {
                this.client = cfg.s3Client();
            }
            if (file.length() < 22) {
                LOG.debug("file empty: {}", file);
                return;
            }
            long start = System.currentTimeMillis();
            LOG.info("s3 putObject start, region:{}, bucket:{}, {} to {}", cfg.region, cfg.bucket, file, key);
            client.putObject(cfg.bucket, key, file);
            LOG.info("s3 putObject finished, cost: {}, region:{}, bucket:{}, {} to {}",
                    System.currentTimeMillis() - start, cfg.region, cfg.bucket, file, key);
        } catch (Exception e) {
            LOG.error("s3 putObject error, region:{}, bucket:{}, {} to {}", cfg.region, cfg.bucket, file, key, e);
        }
    }

    public boolean isEnabled() {
        return client != null;
    }

}
