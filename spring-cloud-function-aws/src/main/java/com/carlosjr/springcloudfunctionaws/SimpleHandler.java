package com.carlosjr.springcloudfunctionaws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SimpleHandler implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String s, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("JDK running " + System.getProperty("java.version"));
        return "Hello world from AWS Lambda! your message is: " + s;
    }
}
