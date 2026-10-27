 package com.ecommerceoms.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {

    private final ObjectMapper objectMapper;
    private final File dataDirectory;

    public JsonManager() {

        objectMapper = new ObjectMapper();

        dataDirectory = new File("data");

        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }
    }

    public <T> void save(String fileName,
                         List<T> data) {

        File file =
                new File(dataDirectory, fileName);

        try {

            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(file, data);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to save data: " +
                    fileName,
                    e
            );
        }
    }

    public <T> List<T> load(
            String fileName,
            TypeReference<List<T>> typeReference) {

        File file =
                new File(dataDirectory, fileName);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {

            return objectMapper.readValue(
                    file,
                    typeReference
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load data: " +
                    fileName,
                    e
            );
        }
    }
}