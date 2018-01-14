package no.frtj.frtjurl;

import javax.servlet.http.HttpServletRequest;

public class FrtjServletUrlFactory {

    public static FrtjServletUrl toUrl(HttpServletRequest request) {
        FrtjUrl frtjUrl = FrtjUrlFactory.toUrl(request.getRequestURL() + "?" + request.getQueryString());
        FrtjServletUrl frtjServletUrl = from(frtjUrl);
        frtjServletUrl.contextPath = FrtjUrlPath.create(request.getContextPath());
        frtjServletUrl.servletPath = FrtjUrlPath.create(request.getServletPath());
        return frtjServletUrl;
    }

    public static FrtjServletUrl from(FrtjUrl frtjUrl) {
        FrtjServletUrl frtjServletUrl = new FrtjServletUrl();
        frtjServletUrl.schema = frtjUrl.schema;
        frtjServletUrl.username = frtjUrl.username;
        frtjServletUrl.password = frtjUrl.password;
        frtjServletUrl.host = frtjUrl.host;
        frtjServletUrl.port = frtjUrl.port;
        frtjServletUrl.path = frtjUrl.path;
        frtjServletUrl.fragment = frtjUrl.fragment;
        frtjServletUrl.query = frtjUrl.query;
        frtjServletUrl.isHttps = frtjUrl.isHttps;
        frtjServletUrl.isHttp = frtjUrl.isHttp;
        frtjServletUrl.isResource = frtjUrl.isResource;
        return frtjServletUrl;
    }
}
