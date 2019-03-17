package com.k9b9.ddb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * DynDb
 */
public class DynDb {

    public AmazonDynamoDB amazonDynamoDbClient;
    public DynamoDB dynamoDb;
    public DynamoDBMapper dynamoDbMapper;
    // I think this can be replaced with DynamoDBMapperConfig.DEFAULT
    // public DynamoDBMapperConfig dynamoDbMapperConfig;
    public DynamoDBMapperConfig ddbCfgDefault = DynamoDBMapperConfig.DEFAULT;

    /**
     * Connects using explicit credentials
     */
    public DynDb(String awsAccessKey, String awsSecretKey, String awsRegion) {

        this.amazonDynamoDbClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .withRegion(awsRegion).build();

        this.dynamoDb = new DynamoDB(this.amazonDynamoDbClient);

        this.dynamoDbMapper = new DynamoDBMapper(this.amazonDynamoDbClient);

        // this.dynamoDbMapperConfig = DynamoDBMapperConfig.builder()
        //         .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT).build();

    }

    /**
     * Connects using Default AWS Credentials (~/.aws/credentials) and region (~/.aws/config),
     * using the DefaultAWSCredentialsProviderChain and DefaultAwsRegionProviderChain chain
     * Table name is specified on the Entity @DynamoDBTable
     */
    public DynDb() {

        this.amazonDynamoDbClient = AmazonDynamoDBClientBuilder.defaultClient();

        this.dynamoDb = new DynamoDB(this.amazonDynamoDbClient);

        this.dynamoDbMapper = new DynamoDBMapper(this.amazonDynamoDbClient);

        // this.dynamoDbMapperConfig = DynamoDBMapperConfig.builder()
        //         .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
        //         .build();
    }
}