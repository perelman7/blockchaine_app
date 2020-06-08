package com.ether.web3test.web3.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wavesplatform.wavesj.DataEntry;
import com.wavesplatform.wavesj.Transaction;
import com.wavesplatform.wavesj.transactions.DataTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ConverterDataTransaction {

    @Autowired
    private ObjectMapper objectMapper;

    public Collection<DataEntry<?>> convertObject(Object obj) {
        Collection<DataEntry<?>> result = new ArrayList<>();
        try {
            Class<?> aClass = obj.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                String key = declaredField.getName();
                Class<?> type = declaredField.getType();
                if (type.equals(String.class)) {
                    String value = declaredField.get(obj) != null ? declaredField.get(obj).toString() : null;
                    result.add(new DataEntry.StringEntry(key, value));
                } else if (type.equals(Long.class) || type.equals(Integer.class)) {
                    Long value = (Long) declaredField.get(obj);
                    result.add(new DataEntry.LongEntry(key, value));
                } else if (type.equals(Boolean.class)){
                    String strValue = declaredField.get(obj) != null ? declaredField.get(obj).toString() : null;
                    boolean value = Boolean.parseBoolean(strValue);
                    result.add(new DataEntry.BooleanEntry(key, value));
                }else{
                    result.add(new DataEntry.StringEntry(key, null));
                    log.warn("Unknown type: {}", type.getName());
                }
            }
        }catch (Exception e){
            log.error("Convert object error, message: {}", e.getMessage());
        }
        return result;
    }

    public <T> T convert(Transaction transaction, Class<T> tClass){
        T result = null;
        if(transaction instanceof DataTransaction){
            Collection<DataEntry<?>> data1 = ((DataTransaction) transaction).getData();
            Map<String, Object> converted = new HashMap<>();
            for (DataEntry<?> dataEntry : data1) {
                converted.put(dataEntry.getKey(), dataEntry.getValue());
            }
            result = objectMapper.convertValue(converted, tClass);
        }
        return result;
    }
}
