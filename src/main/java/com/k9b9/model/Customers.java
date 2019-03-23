package com.k9b9.model;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.k9b9.ddb.SingleTableDdb;
import com.k9b9.dto.Dto;
import com.k9b9.dto.DtoUtils;

/**
 * Users
 */
public class Customers {

    public final static String SKEY = "customer";
    private SingleTableDdb ddb;

    public Customers(SingleTableDdb ddb) {
        this.ddb = ddb;
    }

	public boolean putCustomer(Dto dto) {
		// build Item
        Item item = DtoUtils.toItem(dto);
        this.ddb.putItem(item);
        // TODO add metrics for capacity used
        return true;
    }
}