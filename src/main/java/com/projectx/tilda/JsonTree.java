package com.projectx.tilda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projectx.tilda.tree.JsonTreeNode;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class JsonTree {

    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        URL systemResource = ClassLoader.getSystemResource("test1.json");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(systemResource.getFile())))){
            Object o = gson.fromJson(bufferedReader, Object.class);
            if(o instanceof List){
                System.out.println("JsonArray: "+ o.getClass());
                JsonTreeNode<String,Object> jsonTreeNode = new JsonTreeNode<>();
                jsonTreeNode.setKey("ROOT");
                JsonTreeNode tree = createTree((List<Object>) o, jsonTreeNode);
                printTree(tree,1);
            } else if(o instanceof Map){
                System.out.println("JsonObject:"+o.getClass());
                JsonTreeNode<String,Object> jsonTreeNode = new JsonTreeNode<>();
                jsonTreeNode.setKey("ROOT");
                JsonTreeNode tree = createTree((Map<String, Object>) o, jsonTreeNode);
                printTree(tree,1);
            } else{
                System.out.println("Primitives: " +o.getClass());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printTree(JsonTreeNode root,int numTabs){
        if(root==null){
            System.out.println();
            return;
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<numTabs;i++){
                stringBuilder.append("\t");
            }
            System.out.print(stringBuilder.toString());
            if(root.getValue()==null){
                System.out.println(root.getKey());

            }else{
                System.out.print(root.getKey() + " : " + root.getValue());
            }
            JsonTreeNode child = root.getChild();
            if(child!=null){
                numTabs+=2;
            }
            while(child!=null){
                printTree(child,numTabs);
                child = child.getNext();
            }
            System.out.println();



        }
    }

    
    public static JsonTreeNode createTree(Map<String,Object> objectMap, JsonTreeNode parent){
        if(objectMap!=null) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                JsonTreeNode jsonTreeNode = new JsonTreeNode();
                String k = entry.getKey();
                Object v = entry.getValue();
                jsonTreeNode.setKey(k);
                if (v instanceof List) {
                    jsonTreeNode = createTree((List<Object>) v,jsonTreeNode);
                } else if (v instanceof Map) {
                    jsonTreeNode =  createTree((Map<String, Object>) v,jsonTreeNode);
                } else {
                    jsonTreeNode.setValue(v);
                }
                jsonTreeNode.setParent(parent);
                JsonTreeNode child = parent.getChild();
                JsonTreeNode ptr = parent.getChild();
                while(child!=null && child.getNext()!=null){
                    child = child.getNext();
                }
                if(child==null){
                    child = jsonTreeNode;
                    ptr = child;
                }else{
                    child.setNext(jsonTreeNode);
                }
                parent.setChild(ptr);

            }
            return parent;

        }
        return null;
    }

    public static JsonTreeNode createTree(List<Object> objectList, JsonTreeNode parent){
        if(objectList!=null) {
            for (int i=0;i<objectList.size() ; i++) {
                Object obj = objectList.get(i);
                JsonTreeNode jsonTreeNode = new JsonTreeNode();
                jsonTreeNode.setKey(i);
                if (obj instanceof List) {
                    jsonTreeNode = createTree((List<Object>) obj,jsonTreeNode);
                } else if (obj instanceof Map) {
                    jsonTreeNode =  createTree((Map<String, Object>) obj,jsonTreeNode);
                } else {
                    jsonTreeNode.setValue(obj);
                }
                jsonTreeNode.setParent(parent);
                JsonTreeNode child = parent.getChild();
                JsonTreeNode ptr = parent.getChild();
                while(child!=null && child.getNext()!=null){
                    child = child.getNext();
                }
                if(child==null){
                    child = jsonTreeNode;
                    ptr = child;
                }else{
                    child.setNext(jsonTreeNode);
                }
                parent.setChild(ptr);
            }
            return parent;

        }
        return null;
    }


}
