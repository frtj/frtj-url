package no.frtj.frtjurl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FrtjUrlPath {
    List<String> elements;

    public String getElement(int i) {
        return elements.get(i);
    }

    public boolean isRoot() {
        if(elements==null || elements.size() == 0) {
            return true;
        }
        return false;
    }

    public int numOfElements() {
        return elements.size();
    }

    public FrtjUrlPath subPath(int from) {
        FrtjUrlPath frtjUrlPath = new FrtjUrlPath();
        List<String> sub = new ArrayList<>();
        for(int i = from; i < elements.size(); i++) {
            sub.add(elements.get(i));
        }
        frtjUrlPath.elements = sub;
        return frtjUrlPath;
    }

    public FrtjUrlPath subPath(int from, int to) {
        FrtjUrlPath frtjUrlPath = new FrtjUrlPath();
        List<String> sub = new ArrayList<>();
        for(int i = from; i < elements.size() && i <= to; i++) {
            sub.add(elements.get(i));
        }
        frtjUrlPath.elements = sub;
        return frtjUrlPath;
    }

    public FrtjUrlPath append(String s) {
        FrtjUrlPath frtjUrlPath = new FrtjUrlPath();
        List<String> sub = new ArrayList<>();
        sub.addAll(this.elements);
        sub.add(s);
        frtjUrlPath.elements = sub;
        return frtjUrlPath;
    }

    public FrtjUrlPath normalize() {
        /*
        $expectations = [
    ['bar', '../bar'],
    ['bar', './bar'],
    ['bar', '.././bar'],
    ['bar', '.././bar'],
    ['/foo/bar', '/foo/./bar'],
    ['/bar/', '/bar/./'],
    ['/', '/.'],
    ['/bar/', '/bar/.'],
    ['/bar', '/foo/../bar'],
    ['/', '/bar/../'],
    ['/', '/..'],
    ['/', '/bar/..'],
    ['/foo/', '/foo/bar/..'],
    ['', '.'],
    ['', '..'],
];
         */
        FrtjUrlPath frtjUrlPath = new FrtjUrlPath();
        LinkedList<String> sub = new LinkedList<>();
        for (String element : elements) {
            if(".".equals(element))
                continue;
            if("..".equals(element) && sub.size() == 0 )
                continue;
            if("..".equals(element)){
                sub.pollLast();
                continue;
            }
            sub.add(element);
        }
        frtjUrlPath.elements = sub;
        return frtjUrlPath;
    }

    public static FrtjUrlPath create(String s) {
        FrtjUrlPath urlPath = new FrtjUrlPath();

        if(!s.startsWith("/")) {
            throw new RuntimeException("Path does not start with slash");
        }
        String substring = s.substring(1); // skip first /
        if (StringUtil.isNotEmpty(substring)) {
            String[] split = substring.split("/");
            if (split != null && split.length > 0) {
                List<String> elem = new ArrayList<String>();
                for (String s1 : split) {
                    if (StringUtil.isEmpty(s1)) {
                        throw new RuntimeException("Illegal path found, empty element");
                    }
                    elem.add(FrtjUrlFactory.urlDecode(s1));
                }
                urlPath.elements = elem;
            } else {
                throw new RuntimeException("Illegal path found");
            }
        }

        return urlPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FrtjUrlPath)) return false;

        FrtjUrlPath that = (FrtjUrlPath) o;

        return elements != null ? elements.equals(that.elements) : that.elements == null;
    }

    @Override
    public int hashCode() {
        return elements != null ? elements.hashCode() : 0;
    }
}
