package com.projectx.tilda.tree;

import java.util.List;
import java.util.Map;

public class JsonTreeNode<K,V> {
    private K key;
    private V value;
    private JsonTreeNode parent;
    private JsonTreeNode child;
    private JsonTreeNode next;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public JsonTreeNode getParent() {
        return parent;
    }

    public void setParent(JsonTreeNode parent) {
        this.parent = parent;
    }

    public JsonTreeNode getChild() {
        return child;
    }

    public void setChild(JsonTreeNode child) {
        this.child = child;
    }

    public JsonTreeNode getNext() {
        return next;
    }

    public void setNext(JsonTreeNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JsonTreeNode{").append("\n");
        treeAsString(this,sb,1);
        sb.append("\n}");
        return sb.toString();
    }

    void treeAsString(JsonTreeNode root, StringBuilder sb, int numTabs){
        if(root!=null) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<numTabs;i++){
                stringBuilder.append("\t");
            }
            sb.append(stringBuilder);
            if(root.getValue()==null){
                sb.append(root.getKey()).append(":").append("\n");

            }else{
                sb.append(root.getKey()).append(":").append(root.getValue());
            }
            JsonTreeNode child = root.getChild();
            if(child!=null){
                numTabs+= 1;
            }
            while(child!=null){
                treeAsString(child,sb,numTabs);
                child = child.getNext();
            }
        }
        sb.append("\n");
    }

    public static boolean find(JsonTreeNode jsonTreeNode,String path){
        // "$.emergencyContacts.*";
        if(jsonTreeNode==null || "".equalsIgnoreCase(path)){
            return false;
        }
        else if(!path.contains(".") && jsonTreeNode.getKey()!=null && jsonTreeNode.getKey().equals(path)){
            System.out.println("FOUND: "+jsonTreeNode.getKey()+":"+jsonTreeNode.getValue());
            return true;
        }
        else if(path.contains(".") && jsonTreeNode.getKey()!=null && jsonTreeNode.getKey().equals(path.substring(0,path.indexOf(".")))){
            if(!find(jsonTreeNode.getChild(),path.substring(path.indexOf(".")+1))){
                return false;
            }
            return true;
        } else{
            return find(jsonTreeNode.getNext(),path);
        }
    }

    public static JsonTreeNode createTree(Map<String,Object> objectMap, JsonTreeNode parent){
        if(objectMap!=null) {
            for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
                String k = entry.getKey();
                Object v = entry.getValue();
                JsonTreeNode<String,Object> jsonTreeNode = getJsonTreeNode(v);
                jsonTreeNode.setKey(k);
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
                JsonTreeNode<String,Object> jsonTreeNode = getJsonTreeNode(obj);
                jsonTreeNode.setKey(String.valueOf(i));
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

    private static JsonTreeNode getJsonTreeNode(Object obj) {
        JsonTreeNode jsonTreeNode = new JsonTreeNode();
        if (obj instanceof List) {
            jsonTreeNode = createTree((List<Object>) obj,jsonTreeNode);
        } else if (obj instanceof Map) {
            jsonTreeNode =  createTree((Map<String, Object>) obj,jsonTreeNode);
        } else {
            jsonTreeNode.setValue(obj);
        }
        return jsonTreeNode;
    }
}
