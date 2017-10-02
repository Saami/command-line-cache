package com.interview.cache.impl;

import com.interview.cache.api.Cache;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasiddi on 3/13/17.
 */
public class TextFileCache implements Cache {

    static final Boolean initialized = false;
    private static final String MAP_SYMBOL = "=";
    private static final String KEY_NOT_FOUND_MSG = "Key not found";
    private static final String INVALID_CREATE_MSG = "Key <%s> already exists";
    private static final String SUCCESS_MSG = "";

    private File cacheFile;

    public TextFileCache() {
        initialize();
    }

    private void initialize() {
        if (!initialized) {
            try {
                cacheFile = new File("saamiCache.txt");
                cacheFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String create(String key, String value) {
        if (!get(key).equals(KEY_NOT_FOUND_MSG)) {
            return String.format(INVALID_CREATE_MSG, key);
        } else {
            BufferedWriter bufferedWriter = null;
            try {
                FileWriter fileWriter = new FileWriter(cacheFile, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(key);
                bufferedWriter.write(MAP_SYMBOL);
                bufferedWriter.write(value);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return SUCCESS_MSG;
    }

    @Override
    public String update(String key, String value) {
        String oldValue = get(key);
        if (oldValue.equals(KEY_NOT_FOUND_MSG)) {
            return KEY_NOT_FOUND_MSG;
        }

        replaceLine(key, oldValue, value);
        return SUCCESS_MSG;
    }

    private void replaceLine (String key, String oldValue, String newValue) {
        List<String> fileContent = null;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(cacheFile.toPath(), StandardCharsets.UTF_8));


        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(String.format("%s=%s", key, oldValue))) {
                fileContent.set(i, String.format("%s=%s", key, newValue));
                break;
            }
        }

        Files.write(cacheFile.toPath(), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String get(String key) {
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(cacheFile);
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                String[] cachedEntry = line.split("=");
                String cachedKey =  cachedEntry[0];
                if (cachedKey.equals(key)) {
                    return cachedEntry[1];
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return KEY_NOT_FOUND_MSG;
    }

    @Override
    public String delete(String key) {
        String value = get(key);
        if (value.equals(KEY_NOT_FOUND_MSG)) {
            return KEY_NOT_FOUND_MSG;
        }
        List<String> fileContent = null;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(cacheFile.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                String cachedKey = fileContent.get(i);
                if (cachedKey.startsWith(key + MAP_SYMBOL)) {
                    continue;
                }
            }

            Files.write(cacheFile.toPath(), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SUCCESS_MSG;
    }

    @Override
    public String getAll() {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(cacheFile);
            bufferedReader =
                    new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}
