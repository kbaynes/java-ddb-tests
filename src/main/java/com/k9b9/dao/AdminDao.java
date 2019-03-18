package com.k9b9.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.k9b9.ddb.DynDb;
import com.k9b9.entity.AdminItem;

/**
 * AdminDao
 */
public class AdminDao {

    private DynDb dynDb;

    public AdminDao(DynDb dynDb) {
        this.dynDb = dynDb;
    }

    /**
     * Create and Update are same operation
     */
    public AdminItem putAdmin(AdminItem admin) {
        this.dynDb.dynamoDbMapper.save(admin);
        return admin;
    }

	public AdminItem getAdmin(String userId) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(AdminItem.SKEY));
        eav.put(":val2", new AttributeValue().withS(userId));

        DynamoDBScanExpression ddbScanExpression = new DynamoDBScanExpression()
            .withFilterExpression("skey = :val1 and userid = :val2")
            .withExpressionAttributeValues(eav);

        List<AdminItem> items = dynDb.dynamoDbMapper.scan(AdminItem.class, ddbScanExpression);
        if (items.size()==0) return null;
        return items.get(0);
	}
}