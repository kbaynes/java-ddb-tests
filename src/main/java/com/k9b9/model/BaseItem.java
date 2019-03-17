package com.k9b9.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Item
 */
@DynamoDBTable(tableName = "SingleTableTest")
public class BaseItem {

    public String pkey;
    public String skey;
    public String value;

    public BaseItem() {

    }

    public BaseItem(String pkey, String skey, String value) {
        this.pkey = pkey;
        this.skey = skey;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Item [pkey=" + pkey + ", skey=" + skey + ", value=" + value + "]";
    }

    @DynamoDBHashKey(attributeName = "pkey")
    public String getPkey() {
        return this.pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    @DynamoDBRangeKey(attributeName = "skey")
    public String getSkey() {
        return this.skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    @DynamoDBAttribute(attributeName = "value")
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
