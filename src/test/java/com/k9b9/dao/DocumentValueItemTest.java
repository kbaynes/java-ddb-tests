package com.k9b9.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.entity.DocumentValueItem;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests with a direct DDB connection to a single DynamoDB table. 
 * By default it uses the credentials in ~/.aws/credentials
 * 
 * Many of the tests must be run directly
 * mvn test -Dtest=DocumentValueItemTest#testCreateRandomItem
 */
public class DocumentValueItemTest {

    DocumentValueItemDao dao;

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb("YourKey", "YourSecretKey", "us-east-1", "SingleTableTest");
        this.dao = new DocumentValueItemDao(new SingleTableDdb("SingleTableTest"));
    }

    @Test
    public void testCreateRandomItem() {
        String skey = "docvalue";
        String uuid = java.util.UUID.randomUUID().toString();
        DocumentValueItem item = new DocumentValueItem(uuid, skey, new DocumentValueItem.SimpleDoc("myStrVal", 123, 3.14f));
        item = this.dao.putItem(item);
        assertNotNull(item);
        assertTrue(item.skey.equals(skey));
        System.out.println("CreateRandomItem = "+item);
    }

    @Test
    public void testGetItemsBySortKeyEquals() {
        List<DocumentValueItem> items = this.dao.getItemsBySortKeyEquals("user");
        assertNotNull(items);
        for (DocumentValueItem item : items) {
            System.out.println("ItemBySortKey = "+item);
        }
    }

}