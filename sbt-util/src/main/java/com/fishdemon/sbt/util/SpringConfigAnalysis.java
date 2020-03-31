package com.fishdemon.sbt.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpringConfigAnalysis {

    public static void main(String[] args) {

        FileReader fileReader = new FileReader("eureka-client-config.json");
        String result = fileReader.readString();

        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray jsonArray = JSONUtil.parseArray(jsonObject.get("properties").toString());
        List<SpringConfig> props = JSONUtil.toList(jsonArray, SpringConfig.class);

        Map<String, Object> map = new LinkedHashMap<>();
        props.forEach(ele -> {
            String[] keys = ele.getName().split("\\.");
            Map<String, Object> temp = map;
            for (int i=0; i<keys.length; i++) {
                if (temp instanceof Map && temp.containsKey(keys[i])) {
                    temp = (Map<String, Object>) temp.get(keys[i]);
                } else {
                    temp.put(keys[i], i == keys.length -1 ? ele.getDefaultValue() : new LinkedHashMap<>());
                    temp = i == keys.length -1 ? temp : (Map<String, Object>) temp.get(keys[i]);
                }
            }
        });
    }

    @Data
    public static class SpringConfig {
        private String name;
        private String type;
        private String description;
        private String sourceType;
        private String defaultValue;

    }
}


