package com.k9b9;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

/**
 * IndexServiceTest
 */
public class MainTest {

    AmazonDynamoDB client;
    DynamoDB ddb;
    String tableName = "SingleTable";

    @Before
    public void beforeTest() {
        client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-east-1"))
            .build();
        ddb = new DynamoDB(client);
    }

    @Test
    public void testIsTableExist() {
        Table table = ddb.getTable(tableName);
        System.out.println("TableExist: Table status: " + table.getDescription().getTableStatus());
    }

    @Test
    public void testCreateTable() throws InterruptedException {
        // Table table = ddb.createTable(tableName,
        //         Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
        //                                                                   // key
        //             new KeySchemaElement("title", KeyType.RANGE)), // Sort key
        //         Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
        //             new AttributeDefinition("title", ScalarAttributeType.S)),
        //         new ProvisionedThroughput(10L, 10L));
        //     table.waitForActive();
        //     System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));

        List<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("N"));

        CreateTableRequest request = new CreateTableRequest()
            .withTableName(tableName)
            .withKeySchema(keySchema)
            .withAttributeDefinitions(attributeDefinitions)
            .withProvisionedThroughput(new ProvisionedThroughput()
            .withReadCapacityUnits(5L)
            .withWriteCapacityUnits(6L));

        TableUtils.createTableIfNotExists(client, request);
    }

    @Test
    public void testDeleteTable() throws InterruptedException {
        
        Table table = ddb.getTable(tableName);
        System.out.println("Attempting to delete table; please wait...");
        table.delete();
        table.waitForDelete();
        System.out.print("Delete Table Success.");
        table.waitForActive();
        System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());
    }
}
