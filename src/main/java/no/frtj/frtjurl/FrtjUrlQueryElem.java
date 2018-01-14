package no.frtj.frtjurl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FrtjUrlQueryElem {

    private String key;
    private List<String> value;

    public FrtjUrlQueryElem(String elem) {
        this.key = elem;
    }

    public FrtjUrlQueryElem(String s, String s1) {
        this.key = s;
        this.value = new ArrayList<>();
        this.value.add(s1);
    }

    public FrtjUrlQueryElem(String s, String[] s1) {
        this.key = s;
        this.value = new ArrayList<>();
        this.value.addAll(Arrays.asList(s1));
    }


    public String getKey() {
        return key;
    }

    public String getValue(int i) {
        return value == null || value.size() == 0 ? null : value.get(i);
    }

    public List<String> getValues() {
        return value;
    }

    public int size() {
        return value == null ? 0 : value.size();
    }

    public void addValue(String s) {
        if(value==null)
            value = new ArrayList<>();
        value.add(s);
    }
}
