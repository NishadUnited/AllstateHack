package com.example.data;

import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "data")
public class DataEntity {
    private DBObject data;
    private String signature;

    public DBObject getData() {
        return data;
    }

    public void setData(DBObject data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
