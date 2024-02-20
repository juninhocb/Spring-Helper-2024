package com.carlosjr.awss3trigger;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LambdaHandler implements RequestHandler<S3Event, String> {
    private static final String REGION = "sa-east-1";
    private final AmazonS3 s3Client = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.fromName(REGION))
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();
    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();
        String bucketName = s3Event.getRecords().get(0).getS3().getBucket().getName();
        String fileName = s3Event.getRecords().get(0).getS3().getObject().getKey();
        logger.log("Bucket Name >>> " + bucketName);
        logger.log("File Name >>> " + fileName);
        try (InputStream inputStream = s3Client.getObject(bucketName, fileName).getObjectContent()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ( (line = br.readLine()) != null ){
                logger.log(line);
            }

        } catch (IOException e) {
            return "Fail on reading s3 bucket due " + e.getMessage();
        }
        return "Successfully read from s3 Bucket!";
    }
}
