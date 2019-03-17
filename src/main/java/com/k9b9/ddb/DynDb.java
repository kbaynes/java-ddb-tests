package com.k9b9.ddb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.k9b9.model.BaseItem;

/**
 * DynDb
 */
public class DynDb {

    AmazonDynamoDB amazonDynamoDbClient;
    DynamoDB dynamoDb;
    DynamoDBMapper dynamoDbMapper;
    DynamoDBMapperConfig dynamoDbMapperConfig;

    public DynDb(String tableName, String awsAccessKey, String awsSecretKey, String awsRegion) {
        
        this.amazonDynamoDbClient = AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
            .withRegion(awsRegion)
            .build();

        this.dynamoDb = new DynamoDB(this.amazonDynamoDbClient);

        this.dynamoDbMapper = new DynamoDBMapper(this.amazonDynamoDbClient);
        
        this.dynamoDbMapperConfig = DynamoDBMapperConfig.builder()
            .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
            .build();
    }

    public String getTableStatus(String tableName) throws InterruptedException {
        Table table = this.dynamoDb.getTable(tableName);
        table.waitForActive();
        return table.getDescription().getTableStatus();
    }

    public List<String> listTables() {
        TableCollection<ListTablesResult> tables = this.dynamoDb.listTables();
        Iterator<Table> iterator = tables.iterator();
        List<String> list = new ArrayList<String>();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            list.add(table.getTableName());
        }
        return list;
    }

    /**
     * Create and Update are same operation
     */
    public BaseItem putBaseItem(String pkey, String skey, String value) {
        BaseItem item = new BaseItem(pkey, skey, value);
        dynamoDbMapper.save(item);
        return item;
    }

    public BaseItem getBaseItem(String pkey, String skey) {
        BaseItem item = dynamoDbMapper.load(BaseItem.class, pkey, skey, this.dynamoDbMapperConfig);
        return item;
    }

    public void deleteBaseItem(String pkey, String skey) {
        BaseItem item = dynamoDbMapper.load(BaseItem.class, pkey, skey, this.dynamoDbMapperConfig);
        dynamoDbMapper.delete(item);
    }

    /**
     * Gets all items of the given sort key
     */
    public List<BaseItem> getBaseItemsBySortKeyEquals(String skey) {

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(skey));

        DynamoDBScanExpression ddbScanExpression = new DynamoDBScanExpression()
            .withFilterExpression("skey = :val1")
            .withExpressionAttributeValues(eav);

        List<BaseItem> items = dynamoDbMapper.scan(BaseItem.class, ddbScanExpression);
        return items;
    }
}