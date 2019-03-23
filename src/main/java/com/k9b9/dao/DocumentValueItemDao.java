package com.k9b9.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.entity.DocumentValueItem;

/**
 * DocumentValueItemDao
 */
public class DocumentValueItemDao {

    private SingleTableDdb dynDb;

    public DocumentValueItemDao(SingleTableDdb dynDb) {
        this.dynDb = dynDb;
    }

    /**
     * Create and Update are same operation
     */
    public DocumentValueItem putItem(DocumentValueItem item) {
        dynDb.dynamoDbMapper.save(item);
        // example overriding DocumentValueItem@DynamoDBTableName
        // dynamoMapper.save(myEntity, new dynDb.dynamoDbMapperConfig(new TableNameOverride(this.tableName)));
        return item;
    }

    public DocumentValueItem getItem(String pkey, String skey) {
        DocumentValueItem item = dynDb.dynamoDbMapper.load(DocumentValueItem.class, pkey, skey, this.dynDb.ddbCfgDefault);
        return item;
    }

    public void deleteItem(String pkey, String skey) {
        DocumentValueItem item = dynDb.dynamoDbMapper.load(DocumentValueItem.class, pkey, skey, this.dynDb.ddbCfgDefault);
        dynDb.dynamoDbMapper.delete(item);
    }

    /**
     * Gets all items of the given sort key
     */
    public List<DocumentValueItem> getItemsBySortKeyEquals(String skey) {

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(skey));

        DynamoDBScanExpression ddbScanExpression = new DynamoDBScanExpression().withFilterExpression("skey = :val1")
                .withExpressionAttributeValues(eav);

        List<DocumentValueItem> items = dynDb.dynamoDbMapper.scan(DocumentValueItem.class, ddbScanExpression);
        return items;
    }
}