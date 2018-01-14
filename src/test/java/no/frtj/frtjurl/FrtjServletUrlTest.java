package no.frtj.frtjurl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class FrtjServletUrlTest {

    @Test
    public void should_parse_from_http_servlet_request() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("http://example.com/a/b/c/d");
        HttpServletRequest mock = mock(HttpServletRequest.class);
        doReturn("/b").when(mock).getServletPath();
        doReturn("/a").when(mock).getContextPath();
        doReturn(buffer).when(mock).getRequestURL();
        doReturn(null).when(mock).getQueryString();

        FrtjServletUrl result = FrtjServletUrlFactory.toUrl(mock);

        Assertions.assertThat(result.schema).isEqualTo("http");
        Assertions.assertThat(result.isHttp).isTrue();
        Assertions.assertThat(result.host).isEqualTo("example.com");
        Assertions.assertThat(result.path.getElement(0)).isEqualTo("a");

        Assertions.assertThat(result.contextPath).isEqualTo(FrtjUrlPath.create("/a"));
        Assertions.assertThat(result.servletPath).isEqualTo(FrtjUrlPath.create("/b"));

    }
}