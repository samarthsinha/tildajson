package com.projectx.tilda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projectx.tilda.tree.JsonTreeNode;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class JsonTree {

    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        URL systemResource = ClassLoader.getSystemResource("test1.json");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(systemResource.getFile())))){
            Object o = gson.fromJson(bufferedReader, Object.class);
            if(o instanceof List){
                System.out.println("JsonArray: "+ o.getClass());
                JsonTreeNode<String,Object> jsonTreeNode = new JsonTreeNode<>();
                jsonTreeNode.setKey("$");
                JsonTreeNode tree = JsonTreeNode.createTree((List<Object>) o, jsonTreeNode);
                long startTime = System.nanoTime();
                System.out.println(tree);
                long end = System.nanoTime();
                System.out.println((end-startTime)/1E6 + " ms");
                System.out.println(JsonTreeNode.find(tree,"$.2.name"));
                System.out.println((System.nanoTime()-end)/1E6 + " ms");
            } else if(o instanceof Map){
                System.out.println("JsonObject:"+o.getClass());
                JsonTreeNode<String,Object> jsonTreeNode = new JsonTreeNode<>();
                jsonTreeNode.setKey("$");
                JsonTreeNode tree = JsonTreeNode.createTree((Map<String, Object>) o, jsonTreeNode);
                long startTime = System.nanoTime();
                System.out.println(tree);
                long end = System.nanoTime();
                System.out.println((end-startTime)/1E6 + " ms");
                System.out.println(JsonTreeNode.find(tree,"$.id"));
                System.out.println((System.nanoTime()-end)/1E6 + " ms");
            } else{
                System.out.println("Primitives: " +o.getClass());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    



}
