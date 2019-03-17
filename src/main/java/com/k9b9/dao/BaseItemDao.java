package com.k9b9.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.k9b9.ddb.DynDb;
import com.k9b9.entity.BaseItem;

/**
 * BaseItemDao
 */
public class BaseItemDao {

    private DynDb dynDb;

    public BaseItemDao(DynDb dynDb) {
        this.dynDb = dynDb;
    }

    /**
     * Create and Update are same operation
     */
    public BaseItem putBaseItem(String pkey, String skey, String value) {
        BaseItem item = new BaseItem(pkey, skey, value);
        dynDb.dynamoDbMapper.save(item);
        // example overriding BaseItem@DynamoDBTableName
        // dynamoMapper.save(myEntity, new dynDb.dynamoDbMapperConfig(new TableNameOverride(this.tableName)));
        return item;
    }

    public BaseItem getBaseItem(String pkey, String skey) {
        BaseItem item = dynDb.dynamoDbMapper.load(BaseItem.class, pkey, skey, this.dynDb.ddbCfgDefault);
        return item;
    }

    public void deleteBaseItem(String pkey, String skey) {
        BaseItem item = dynDb.dynamoDbMapper.load(BaseItem.class, pkey, skey, this.dynDb.ddbCfgDefault);
        dynDb.dynamoDbMapper.delete(item);
    }

    /**
     * Gets all items of the given sort key
     */
    public List<BaseItem> getBaseItemsBySortKeyEquals(String skey) {

        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(skey));

        DynamoDBScanExpression ddbScanExpression = new DynamoDBScanExpression().withFilterExpression("skey = :val1")
                .withExpressionAttributeValues(eav);

        List<BaseItem> items = dynDb.dynamoDbMapper.scan(BaseItem.class, ddbScanExpression);
        return items;
    }
}