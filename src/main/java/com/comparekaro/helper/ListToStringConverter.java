package com.comparekaro.helper;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ListToStringConverter implements AttributeConverter<List<String>, Array> {
    @Override
    public Array convertToDatabaseColumn(List<String> attribute) {

        return null;
    }

    @Override
    public List<String> convertToEntityAttribute(Array dbData) {
        List<String> list = new ArrayList<>();
        try {
            String[] receivedArray = (String[]) dbData.getArray();
            list = new ArrayList<>(Arrays.asList(receivedArray));
        } catch (SQLException e) {
            log.error("error while converting to entity attribute");
            e.printStackTrace();
        }
        return list;
    }
}
