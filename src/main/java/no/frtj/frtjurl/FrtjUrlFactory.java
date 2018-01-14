package no.frtj.frtjurl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class FrtjUrlFactory {

    //https://en.wikipedia.org/wiki/Uniform_Resource_Identifier#Syntax

    //schema:[//[user[:password]@]host[:port]][/path][?query][#fragment]

    //https://stackoverflow.com/questions/2924160/is-it-valid-to-have-more-than-one-question-mark-in-a-url

    public static FrtjUrl toUrl(String s) {
        FrtjUrl myUrl = new FrtjUrl();

        String rest = s;
        String schemaPart = null;
        boolean isHttps = false;
        boolean isHttp = false;
        if (s.startsWith("https://")) {
            isHttps = true;
            schemaPart = "https";
            rest = s.substring(8);
        } else if (s.startsWith("http://")) {
            isHttp = true;
            schemaPart = "http";
            rest = s.substring(7);
        }


        if (!isHttp && !isHttps && s.contains("://")) {
            throw new RuntimeException("Unknown schema. Support only http and https atm");
        }


        int firstSlash = rest.indexOf("/");
        int firstQuestion = rest.indexOf("?");
        int firstHashBang = rest.indexOf("#");
        int firstAt = rest.indexOf("@");

        String authorityPart = null;
        if (firstSlash >= 0) {
            String auth = rest.substring(0,firstSlash);
            String r = rest.substring(firstSlash);
            String[] split = rest.split("/", 2);
            //authorityPart = split[0];
            authorityPart = auth;
            //rest = split[1];
            rest = r;
        } else if (firstSlash == -1 && firstQuestion >= 0) {
            String[] split = rest.split("\\?", 2);
            authorityPart = split[0];
            rest = split[1];
        } else if (firstSlash == -1 && firstQuestion == -1 && firstHashBang >= 0) {
            String[] split = rest.split("#", 2);
            authorityPart = split[0];
            rest = split[1];
        } else if (firstSlash == -1 && firstQuestion == -1 && firstHashBang == -1) {
            authorityPart = rest;
        }

        if (firstQuestion >= 0 && firstSlash >= 0 && firstQuestion < firstSlash) {
            throw new RuntimeException("Question mark found before path character ... this is not good");
        }
        if (firstHashBang >= 0 && firstSlash >= 0 && firstHashBang < firstSlash) {
            throw new RuntimeException("Hashbang character found before path character ... this is not good");
        }


        String pathPart = null;
        if (firstSlash >= 0 && firstQuestion == -1 && firstHashBang == -1) {
            pathPart = rest;
        } else if (firstSlash >= 0 && firstQuestion >= 0) {
            String[] split = rest.split("\\?", 2);
            pathPart = split[0];
            rest = split[1];
        } else if (firstSlash >= 0 && firstQuestion == -1 && firstHashBang >= 0) {
            String[] split = rest.split("#", 2);
            pathPart = split[0];
            rest = split[1];
        }

        String queryPart = null;
        if (firstQuestion >= 0 && firstHashBang == -1) {
            queryPart = rest;
        } else if (firstQuestion >= 0 && firstHashBang >= 0) {
            String[] split = rest.split("#", 2);
            queryPart = split[0];
            rest = split[1];
        }

        String fragmentPart = null;
        if (firstHashBang >= 0) {
            fragmentPart = rest;
        }


        if (StringUtil.isEmpty(authorityPart) && StringUtil.isEmpty(pathPart)) {
            throw new RuntimeException("host is missing");
        }

        if ((isHttp || isHttps) && StringUtil.isEmpty(authorityPart)) {
            throw new RuntimeException("host is missing");
        }


        if (!StringUtil.isEmpty(authorityPart)) {
            String hostPortPart = null;
            if (authorityPart.contains("@")) {
                String[] split = authorityPart.split("@");
                if (split[0].contains(":")) {
                    String[] split1 = split[0].split(":", 2);
                    myUrl.username = split1[0];
                    myUrl.password = urlDecode(split1[1]);

                } else {
                    myUrl.username = split[0];
                }

                hostPortPart = split[1];
            } else {
                hostPortPart = authorityPart;
            }

            if (hostPortPart.contains(":")) {
                String[] split1 = hostPortPart.split(":");
                myUrl.host = split1[0];
                myUrl.port = split1[1];
            } else {
                myUrl.host = hostPortPart;
            }
        }

        if (!StringUtil.isEmpty(queryPart)) {
            myUrl.query = parseQuery(queryPart);
        }

        if (!StringUtil.isEmpty(fragmentPart)) {
            myUrl.fragment = urlDecode(fragmentPart);
        }

        if (!StringUtil.isEmpty(pathPart)) {
            myUrl.path = parsePath(pathPart);
            if (pathPart.endsWith("/")) {
                myUrl.isResource = false;
            } else {
                myUrl.isResource = true;
            }
        } else {
            myUrl.path = new FrtjUrlPath();
        }


        myUrl.isHttps = isHttps;
        myUrl.isHttp = isHttp;
        myUrl.schema = schemaPart;

        return myUrl;
    }

    private static boolean hasSchema(String s) {
        if (s.startsWith("https")) {
            return true;
        } else if (s.startsWith("http")) {
            return true;
        }
        return false;
    }

    private static String parseScheme(String s) {
        if (s.startsWith("https://")) {
            return "https";
        } else if (s.startsWith("http://")) {
            return "http";
        }
        return null;
    }

    private static FrtjUrlPath parsePath(String s) {
        FrtjUrlPath urlPath = FrtjUrlPath.create(s);

        return urlPath;
    }

    private static FrtjUrlQuery parseQuery(String s) {

        Map<String,  FrtjUrlQueryElem> queryELems = new LinkedHashMap<>();
        String[] elems = s.split("[;&]");
        for (String elem : elems) {
            if (elem.contains("=")) {
                String[] split = elem.split("=");
                String key = urlDecode(split[0]);
                if(queryELems.containsKey(key)) {
                    queryELems.get(key).addValue(urlDecode(split[1]));
                } else {
                    queryELems.put(key, new FrtjUrlQueryElem(key, urlDecode(split[1])));
                }
            } else {
                String key = urlDecode(elem);
                queryELems.put(key, new FrtjUrlQueryElem(key));
            }


        }

        FrtjUrlQuery myQuery = new FrtjUrlQuery();
        myQuery.queryElems = new ArrayList<>(queryELems.values());
        return myQuery;
    }

    public static String urlDecode(String s) {
        return urlDecode(s, "UTF-8");
    }

    public static String urlDecode(String s, String encoding) {
        try {
            return URLDecoder.decode(s, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
