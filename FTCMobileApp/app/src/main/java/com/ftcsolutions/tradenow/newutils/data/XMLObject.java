package com.ftcsolutions.tradenow.newutils.data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by akshay on 25-06-2016.
 */
public class XMLObject {

    HashMap<String, String> params;
    List<XMLObject> childs;
    String value;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public List<XMLObject> getChilds() {
        return childs;
    }

    public void setChilds(List<XMLObject> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "XMLObject{" +
                "params=" + params +
                ", childs=" + childs +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
