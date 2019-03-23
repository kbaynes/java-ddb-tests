package com.k9b9.dao;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.k9b9.ddb.SingleTableDdb;

/**
 * ItemDao
 * A data access object which does not use entities, but uses the Item
 * fluent API instead.
 */
public class ItemDao {

    private SingleTableDdb dynDb;

    public ItemDao(SingleTableDdb dynDb) {
        this.dynDb = dynDb;
    }

    public PutItemOutcome putItem(Item item) {
        PutItemOutcome pio = dynDb.putItem(item);
        return pio;
    }

    public PutItemOutcome putValue(String pkey, String skey, String value) {
        PrimaryKey key = new PrimaryKey("pkey",pkey,"skey",skey);
        Item item = new Item()
            .withPrimaryKey(key)
            .withString("value", value);
        PutItemOutcome pio = dynDb.putItem(item);
        return pio;
    }

    public PutItemOutcome putValue(String pkey, String skey, int value) {
        PrimaryKey key = new PrimaryKey("pkey",pkey,"skey",skey);
        Item item = new Item()
            .withPrimaryKey(key)
            .withInt("value", value);
        PutItemOutcome pio = dynDb.putItem(item);
        return pio;
    }

    public PutItemOutcome putValueJson(String pkey, String skey, String json) {
        PrimaryKey key = new PrimaryKey("pkey",pkey,"skey",skey);
        Item item = new Item()
            .withPrimaryKey(key)
            .withJSON("value", json);
        PutItemOutcome pio = dynDb.putItem(item);
        return pio;
    }
}