package no.frtj.frtjurl;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class FrtjUrlPathTest {

    @Test
    public void should_parse_path() {
        FrtjUrlPath path = FrtjUrlPath.create("/a/b");

        Assertions.assertThat(path.getElement(0)).isEqualTo("a");
    }

    @Test
    public void should_create_sub_path() {
        FrtjUrlPath path = FrtjUrlPath.create("/a/b/c");

        FrtjUrlPath result = path.subPath(1);

        Assertions.assertThat(result.getElement(0)).isEqualTo("b");
        Assertions.assertThat(result.numOfElements()).isEqualTo(2);
    }

    @Test
    public void should_create_sub_path2() {
        FrtjUrlPath path = FrtjUrlPath.create("/a/b/c/d");

        FrtjUrlPath result = path.subPath(1, 2);

        Assertions.assertThat(result.getElement(0)).isEqualTo("b");
        Assertions.assertThat(result.getElement(1)).isEqualTo("c");
        Assertions.assertThat(result.numOfElements()).isEqualTo(2);
    }

    @Test
    public void should_append_path() {
        FrtjUrlPath path = FrtjUrlPath.create("/a/b/c");

        FrtjUrlPath result = path.append("d");

        Assertions.assertThat(result.numOfElements()).isEqualTo(4);
        Assertions.assertThat(result.getElement(3)).isEqualTo("d");
    }

    @Test
    public void should_normalize_path() {
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
        FrtjUrlPath path1 = FrtjUrlPath.create("/..").normalize();
        Assertions.assertThat(path1.isRoot()).isTrue();
        Assertions.assertThat(path1.numOfElements()).isEqualTo(0);

        FrtjUrlPath path2 = FrtjUrlPath.create("/.").normalize();
        Assertions.assertThat(path2.isRoot()).isTrue();
        Assertions.assertThat(path2.numOfElements()).isEqualTo(0);

        FrtjUrlPath path3 = FrtjUrlPath.create("/foo/bar/..").normalize();
        Assertions.assertThat(path3.isRoot()).isFalse();
        Assertions.assertThat(path3.numOfElements()).isEqualTo(1);
        Assertions.assertThat(path3.getElement(0)).isEqualTo("foo");
    }
}