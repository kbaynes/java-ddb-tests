package com.k9b9.dto;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DtoUtils
 */
public class DtoUtils {

    public static String toJson(Object dto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String strJson = objectMapper.writeValueAsString(dto);
        return strJson;
    }

    public static Item toItem(Admin admin) {
        if (admin == null)
            return null;
        String jsonValue;
        try {
            jsonValue = DtoUtils.toJson(admin.jsonValue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        PrimaryKey key = new PrimaryKey("pkey", admin.pkey, "skey", admin.skey);
        Item item = new Item().withPrimaryKey(key).withJSON("jsonvalue", jsonValue);
        return item;
    }

    public static Admin toAdmin(Item item) {
        String pkey = item.getString("pkey");
        String skey = item.getString("skey");
        
        // simply for debug - remove from prod
        String json = item.getJSON("jsonvalue");
        System.out.println("toAdmin, json =" + json);
        
        ObjectMapper objectMapper = new ObjectMapper();
        Admin.JsonValue value;
        try {
            value = objectMapper.readValue(json, Admin.JsonValue.class);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
        Admin admin = new Admin(pkey,skey,value);
        return admin;
	}

	public static Item toItem(Dto dto) {
		if (dto == null)
            return null;
        String jsonValueMap = null;
        try {
            jsonValueMap = DtoUtils.toJson(dto.valueMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        PrimaryKey key = new PrimaryKey("pkey", dto.pkey, "skey", dto.skey);
        Item item = new Item().withPrimaryKey(key).withJSON("valueMap", jsonValueMap);
        return item;
    }
    
    public static Dto toDto(Item item) {
        String pkey = item.getString("pkey");
        String skey = item.getString("skey");
        // simply for debug - remove from prod
        String json = item.getJSON("valueMap");
        System.out.println("toDto, json =" + json);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> valueMap;
        try {
            valueMap = objectMapper.readValue(json, Map.class);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
        Dto dto = new Dto(pkey,skey,valueMap);
        return dto;
	}
}