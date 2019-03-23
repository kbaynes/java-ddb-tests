package com.k9b9.dao;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.k9b9.dao.SimpleValueItemDao;
import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.entity.SimpleValueItem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class tests with a direct DDB connection to a single DynamoDB table. 
 * By default it uses the credentials in ~/.aws/credentials
 * 
 * Many of the tests must be run directly
 * mvn test -Dtest=ItemDaoTest#testCreateRandomUser
 */
public class ItemDaoTest {

    ItemDao dao;

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb("YourKey", "YourSecretKey", "us-east-1");
        this.dao = new ItemDao(new SingleTableDdb("SingleTableTest"));
    }

    @Test
    public void testCreateStringValue() {
        String skey = "string-value";
        String uuid = java.util.UUID.randomUUID().toString();
        // storing JSON as a String, not a JSON type!
        PutItemOutcome pio = this.dao.putValue(uuid, skey, String.format("{'date':'%s'}",(new Date()).toString()));
        assertNotNull(pio.getPutItemResult());
        assertTrue(pio.getPutItemResult()!=null);
        System.out.println("CreateStringValue OK");
    }

    @Test
    public void testCreateIntValue() {
        String skey = "int-value";
        String uuid = java.util.UUID.randomUUID().toString();
        // storing JSON as a String, not a JSON type!
        PutItemOutcome pio = this.dao.putValue(uuid, skey, 12345);
        assertNotNull(pio.getPutItemResult());
        assertTrue(pio.getPutItemResult()!=null);
        System.out.println("CreateIntValue OK");
    }



}