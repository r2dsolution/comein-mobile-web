package com.aws.codestar.projecttemplates.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;

@Configuration
@PropertySource("classpath:aws.properties")
public class AWSConfig {

    @Value("${region}")
    public String region;

    @Value("${accessKey}")
    public String accessKey;

    @Value("${secretKey}")
    public String secretKey;

    @Bean
    public AWSSecretsManager initAWSSecretsManager() {
        Regions regions = Regions.fromName(region);
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withCredentials(initCredentialsProvider())
                .withRegion(regions)
                .build();
        return client;
    }

    @Bean
    public AWSCredentialsProvider initCredentialsProvider() {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);

        return new AWSStaticCredentialsProvider(awsCreds);
    }
}