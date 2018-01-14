package no.frtj.frtjurl;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

public class FrtjUrlFactoryTest {

    @Test
    public void parse_wiki()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("https://en.wikipedia.org/wiki/Internet#Terminology");

        Assertions.assertThat(url.schema).isEqualTo("https");
        Assertions.assertThat(url.host).isEqualTo("en.wikipedia.org");

        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("wiki");
        Assertions.assertThat(url.path.getElement(1)).isEqualTo("Internet");
        Assertions.assertThat(url.isResource).isTrue();
        Assertions.assertThat(url.fragment).isEqualTo("Terminology");
    }

    @Test
    public void should_parse_websitebuilders()  {

        FrtjUrl url = FrtjUrlFactory.toUrl("https://websitebuilders.com");

        Assertions.assertThat(url.schema).isEqualTo("https");
        Assertions.assertThat(url.host).isEqualTo("websitebuilders.com");

        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
    }

    @Test
    public void should_parse_host()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("www.vg.com");

        Assertions.assertThat(url.isHttp).isFalse();
        Assertions.assertThat(url.isHttps).isFalse();
        Assertions.assertThat(url.host).isEqualTo("www.vg.com");

        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
    }

    @Test
    public void should_parse_file_path()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("/a/b");

        Assertions.assertThat(url.isHttp).isFalse();
        Assertions.assertThat(url.isHttps).isFalse();

        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("a");
        Assertions.assertThat(url.path.getElement(1)).isEqualTo("b");
        Assertions.assertThat(url.isResource).isTrue();
    }

    @Test
    public void should_parse_directory_path()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("/a/b/");

        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("a");
        Assertions.assertThat(url.path.getElement(1)).isEqualTo("b");
        Assertions.assertThat(url.isResource).isFalse();
    }

    @Test
    public void should_parse_query()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("/a/b?a;b;c=33");

        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("a");
        Assertions.assertThat(url.path.getElement(1)).isEqualTo("b");
        Assertions.assertThat(url.isResource).isTrue();

        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("a");
        Assertions.assertThat(url.query.get(0).getValue(0)).isNull();

        Assertions.assertThat(url.query.get(1).getKey()).isEqualTo("b");
        Assertions.assertThat(url.query.get(1).getValue(0)).isNull();

        Assertions.assertThat(url.query.get(2).getKey()).isEqualTo("c");
        Assertions.assertThat(url.query.get(2).getValue(0)).isEqualTo("33");

    }

    @Test
    public void should_parse4()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.com/blah_blah");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.host).isEqualTo("foo.com");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("blah_blah");
    }

    @Test
    public void should_parse5()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.com/blah_blah_(wikipedia)");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.host).isEqualTo("foo.com");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("blah_blah_(wikipedia)");
    }
    
    @Test
    public void should_parse6()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.com/blah_blah_(wikipedia)_(again)");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.host).isEqualTo("foo.com");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("blah_blah_(wikipedia)_(again)");
    }
    
    @Test
    public void should_parse7()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://www.example.com/wpstyle/?p=364");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.host).isEqualTo("www.example.com");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("wpstyle");
        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("p");
        Assertions.assertThat(url.query.get(0).getValue(0)).isEqualTo("364");
    }

    @Test
    public void should_parse8()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("https://www.example.com/foo/?bar=baz&inga=42&quux");

        Assertions.assertThat(url.schema).isEqualTo("https");
        Assertions.assertThat(url.host).isEqualTo("www.example.com");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("foo");
        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("bar");
        Assertions.assertThat(url.query.get(0).getValue(0)).isEqualTo("baz");
        Assertions.assertThat(url.query.get(1).getKey()).isEqualTo("inga");
        Assertions.assertThat(url.query.get(1).getValue(0)).isEqualTo("42");
        Assertions.assertThat(url.query.get(2).getKey()).isEqualTo("quux");
        Assertions.assertThat(url.query.get(2).getValue(0)).isNull();
    }
    
    @Test
    public void should_parse9()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://✪df.ws/123");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.host).isEqualTo("✪df.ws");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("123");
    }

    @Test
    public void should_parse10()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://userid:password@example.com:8080");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("userid");
        Assertions.assertThat(url.password).isEqualTo("password");
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isEqualTo("8080");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse11()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://userid:password@example.com:8080/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("userid");
        Assertions.assertThat(url.password).isEqualTo("password");
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isEqualTo("8080");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse12()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://userid@example.com");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("userid");
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse13()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://userid@example.com/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("userid");
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse14()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://userid@example.com:8080");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("userid");
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isEqualTo("8080");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse15()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://userid@example.com:8080/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("userid");
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isEqualTo("8080");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse16()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://userid@example.com:8080/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("userid");
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isEqualTo("8080");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse17()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://142.42.1.1/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("142.42.1.1");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse18()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://142.42.1.1:8080/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("142.42.1.1");
        Assertions.assertThat(url.port).isEqualTo("8080");
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse19()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://➡.ws/䨹");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("➡.ws");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("䨹");
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse20()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://⌘.ws");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("⌘.ws");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse21()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://⌘.ws/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("⌘.ws");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
    }

    @Test
    public void should_parse22()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.com/blah_(wikipedia)#cite-1");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("foo.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("blah_(wikipedia)");
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isEqualTo("cite-1");
    }

    @Test
    public void should_parse23()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.com/blah_(wikipedia)_blah#cite-1");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("foo.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("blah_(wikipedia)_blah");
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isEqualTo("cite-1");
    }

    @Test
    public void should_parse24()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.com/unicode_(✪)_in_parens");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("foo.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("unicode_(✪)_in_parens");
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse25()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.com/(something)?after=parens");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("foo.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("(something)");
        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("after");
        Assertions.assertThat(url.query.get(0).getValue(0)).isEqualTo("parens");
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse26()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://☺.damowmow.com/");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("☺.damowmow.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse27()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://code.google.com/events/#&product=browser");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("code.google.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isFalse();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("events");
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNotNull();
        Assertions.assertThat(url.fragment).isEqualTo("&product=browser");
    }

    @Test
    public void should_parse28()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://j.mp");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("j.mp");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse29()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.bar/?q=Test%20URL-encoded%20stuff");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("foo.bar");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("q");
        Assertions.assertThat(url.query.get(0).getValue(0)).isEqualTo("Test URL-encoded stuff");
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse30()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://مثال.إختبار");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("مثال.إختبار");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse31()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://例子.测试");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("例子.测试");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse32()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://उदाहरण.परीक्षा");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("उदाहरण.परीक्षा");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse33()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://-.~_!$&'()*+,;=:%40:80%2f::::::@example.com");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isEqualTo("-.~_!$&'()*+,;=");
        Assertions.assertThat(url.password).isEqualTo("@:80/::::::");
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse34()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://1337.net");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("1337.net");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse35()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://a.b-c.de");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("a.b-c.de");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse36()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://223.255.255.254");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("223.255.255.254");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse37()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://vg.no?a=b");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("vg.no");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("a");
        Assertions.assertThat(url.query.get(0).getValue(0)).isEqualTo("b");
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse38()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://example.com/?foo=bar");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("foo");
        Assertions.assertThat(url.query.get(0).getValue(0)).isEqualTo("bar");
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse39()  {
        FrtjUrl url = FrtjUrlFactory.toUrl("http://example.com/?a=1;a=2;b=3");

        Assertions.assertThat(url.schema).isEqualTo("http");
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.isRoot()).isTrue();
        Assertions.assertThat(url.query).isNotNull();
        Assertions.assertThat(url.query.get(0).getKey()).isEqualTo("a");
        Assertions.assertThat(url.query.get(0).getValue(0)).isEqualTo("1");
        Assertions.assertThat(url.query.get(0).getValue(1)).isEqualTo("2");
        Assertions.assertThat(url.query.get(1).getKey()).isEqualTo("b");
        Assertions.assertThat(url.query.get(1).getValue(0)).isEqualTo("3");
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_parse40() {
        FrtjUrl url = FrtjUrlFactory.toUrl("example.com/a/b/c");

        Assertions.assertThat(url.schema).isNull();
        Assertions.assertThat(url.username).isNull();
        Assertions.assertThat(url.password).isNull();
        Assertions.assertThat(url.host).isEqualTo("example.com");
        Assertions.assertThat(url.port).isNull();
        Assertions.assertThat(url.path).isNotNull();
        Assertions.assertThat(url.path.getElement(0)).isEqualTo("a");
        Assertions.assertThat(url.path.getElement(1)).isEqualTo("b");
        Assertions.assertThat(url.path.getElement(2)).isEqualTo("c");
        Assertions.assertThat(url.query).isNull();
        Assertions.assertThat(url.fragment).isNull();
    }

    @Test
    public void should_fail_01() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("host is missing");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_02() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://?");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("host is missing");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_03() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://#");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("host is missing");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_04() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://??");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("host is missing");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_05() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://??/");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).startsWith("Question mark found before path character");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_06() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://##");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).startsWith("host is missing");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_07() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://##/");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).startsWith("Hashbang character found before path character");
            return;
        }
        fail("should not end up here");
    }

    @Test
    @Ignore // not sure how important it is...
    public void should_fail_08() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http://foo.bar?q=Spaces should be encoded");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).startsWith("Hashbang character found before path character");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_09() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("//");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Illegal path found");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_10() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("//a");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Illegal path found, empty element");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_11() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("///a");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Illegal path found, empty element");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_12() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("///");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Illegal path found");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_13() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("http:///a");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("host is missing");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_14() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl("rdar://1234");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Unknown schema. Support only http and https atm");
            return;
        }
        fail("should not end up here");
    }

    @Test
    public void should_fail_15() {
        try {
            FrtjUrl url = FrtjUrlFactory.toUrl(":// should fail");
        } catch ( RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Unknown schema. Support only http and https atm");
            return;
        }
        fail("should not end up here");
    }


    @Test // todo
    public void hmm() {
        String s = "http://arg:p::@www.vg.no/a/b?d=1&e=2#grynt";
        int firstDoubleColon = s.indexOf("://");
        int firstSlash = s.indexOf("/", firstDoubleColon+"://".length());
        int firstQuestion = s.indexOf("?");
        int firstHashBang = s.indexOf("#");
        int firstAt = s.indexOf("@");

        System.out.println(firstDoubleColon);
        System.out.println(firstSlash);
    }


    
    // http://formvalidation.io/validators/uri/


    //ftp://foo.bar/baz


/*
    Invalid URL examples

    http://.
    http://..
    http://../
    foo.com

    http:// shouldfail.com

    http://foo.bar/foo(bar)baz quux
    ftps://foo.bar/
    http://-error-.invalid/
    http://a.b--c.de/
    http://-a.b.co
    http://a.b-.co
    http://0.0.0.0
    http://10.1.1.0
    http://10.1.1.255
    http://224.1.1.1
    http://1.1.1.1.1
    http://123.123.123
    http://3628126748
    http://.www.foo.bar/
    http://www.foo.bar./
    http://.www.foo.bar./
    http://10.1.1.1
    http://10.1.1.254
    */

}