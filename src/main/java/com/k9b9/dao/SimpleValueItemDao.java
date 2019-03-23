package com.k9b9.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.entity.SimpleValueItem;

/**
 * BaseItemDao
 */
public class SimpleValueItemDao {

    private SingleTableDdb dynDb;

    public SimpleValueItemDao(SingleTableDdb dynDb) {
        this.dynDb = dynDb;
    }

    /**
     * Create and Update are same operation
     */
    public SimpleValueItem putBaseItem(String pkey, String skey, String value) {
        SimpleValueItem item = new SimpleValueItem(pkey, skey, value);
        dynDb.dynamoDbMapper.save(item);
        // example overriding BaseItem@DynamoDBTableName
        // dynamoMapper.save(myEntity, new dynDb.dynamoDbMapperConfig(new TableNameOverride(this.tableName)));
        return item;
    }

    public SimpleValueItem getBaseItem(String pkey, String skey) {
        SimpleValueItem item = dynDb.dynamoDbMapper.load(SimpleValueItem.class, pkey, skey, this.dynDb.ddbCfgDefault);
        return item;
    }

    public void deleteBaseItem(String pkey, String skey) {
        SimpleValueItem item = dynDb.dynamoDbMapper.load(SimpleValueItem.class, pkey, skey, this.dynDb.ddbCfgDefault);
        dynDb.dynamoDbMapper.delete(item);
    }

    /**
     * Gets all items of the given sort key
     */
    public List<SimpleValueItem> getBaseItemsBySortKeyEquals(String skey) {

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(skey));

        DynamoDBScanExpression ddbScanExpression = new DynamoDBScanExpression().withFilterExpression("skey = :val1")
                .withExpressionAttributeValues(eav);

        List<SimpleValueItem> items = dynDb.dynamoDbMapper.scan(SimpleValueItem.class, ddbScanExpression);
        return items;
    }
}