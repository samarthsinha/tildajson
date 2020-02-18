package com.projectx.tilda.tree;

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
        final StringBuilder sb = new StringBuilder("JsonTreeNode{");
        sb.append("key=").append(key);
        sb.append(", val=").append(value);
        sb.append(", child=").append(child);
        sb.append(", next=").append(next);
        sb.append('}');
        return sb.toString();
    }
}
