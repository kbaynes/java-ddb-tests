package com.k9b9.model;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.dto.Dto;

import org.junit.Before;
import org.junit.Test;

/**
 * CustomersTest
 */
public class CustomersTest {

    SingleTableDdb ddb;

    @Before
    public void beforeTest() {
        // this.myDb = new DynDb("YourKey", "YourSecretKey", "us-east-1", "SingleTableTest");
        this.ddb = new SingleTableDdb("SingleTableTest");
    }

    @Test
    public void testCreateRandomCustomer() {

        Customers customers = new Customers(this.ddb);

        Map<String,String> addressMap = new HashMap<String,String>();
        addressMap.put("streetAddress", "123 Main Street");
        addressMap.put("City", "Raleigh");
        addressMap.put("State", "NC");
        addressMap.put("ZIP", "27615");
        
        Map<String,Object> valueMap = new HashMap<String,Object>();
        valueMap.put("customerName","Jane Doe");
        valueMap.put("customerEmail","email@server.com");
        valueMap.put("customerAddress",addressMap);

        Dto customer = new Dto(java.util.UUID.randomUUID().toString(), Customers.SKEY, valueMap);
        System.out.println(customer.toString());
        assertTrue(customers.putCustomer(customer));
        System.out.println("CreateRandomCustomer OK");
    }
}