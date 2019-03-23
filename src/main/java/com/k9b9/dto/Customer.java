package com.k9b9.dto;

import java.util.List;
import java.util.Map;

/**
 * Customer
 */
public class Customer implements DtoInterface {

    public String pkey;
    public String skey;
    public Map<String,String> valueMap;

    public Customer(String pkey, String skey, Map<String,String> valueMap) {
        this.pkey = pkey;
        this.skey = skey;
        this.valueMap = valueMap;
    }
}