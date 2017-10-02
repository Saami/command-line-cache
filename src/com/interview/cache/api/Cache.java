package com.interview.cache.api;

import java.util.List;

/**
 * Created by sasiddi on 3/13/17.
 */
public interface Cache {
    String create(String key, String value);
    String update(String key, String value);
    String get(String key);
    String delete(String key);
    String getAll();
}
