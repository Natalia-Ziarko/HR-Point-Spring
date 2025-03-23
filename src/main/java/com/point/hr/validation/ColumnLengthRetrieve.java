package com.point.hr.validation;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

@Service // INFO: Fetch column lengths only at the start of deploying
public class ColumnLengthRetrieve {

    public static final int WRONG_COLUMN_LENGTH = -1;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Map<String, Integer> lengthMap = new HashMap<>();

    @PostConstruct
    public void init() {
        loadColumnLengths();
    }

    private void loadColumnLengths() {
        // INFO: Choose restricted tables and columns
        String[] peopleColumnNames = {
                "perSocialNo", "perFirstName", "perLastName",
                "perCity", "perZipCode", "perStreet",
                "perBuildNo", "perApartNo"
        };

        cacheColumnLength("people", peopleColumnNames);
    }

    private void cacheColumnLength(String tableName, String[] columnNames) {
        for (String colName : columnNames) {
            int length = retrieveColumnLengthFromDB(tableName, colName);
            lengthMap.put(tableName + "." + colName, length);
        }
    }

    private int retrieveColumnLengthFromDB(String tableName, String columnName) {
        String sql = "SELECT CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS " +
                "WHERE TABLE_NAME = :tableName AND COLUMN_NAME = :columnName";

        try {
            Object result = entityManager.createNativeQuery(sql)
                    .setParameter("tableName", tableName.toUpperCase())
                    .setParameter("columnName", columnName.toUpperCase())
                    .getSingleResult();

            return (result != null) ? ((Number) result).intValue() : WRONG_COLUMN_LENGTH;
        } catch (Exception e) {
            return WRONG_COLUMN_LENGTH;
        }
    }

    public static int getColumnLength(String tableName, String columnName) {
        return lengthMap.getOrDefault(tableName + "." + columnName, WRONG_COLUMN_LENGTH);
    }
}
