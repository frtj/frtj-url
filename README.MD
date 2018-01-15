## ** **Under Construction, Alpha version Status, everything may change anytime or not at all** **

A simple lib to represent an URL.

```
FrtjUrl url = FrtjUrlFactory.toUrl("https://example.com");
```

..or if you are dealing with like, servlet and stuff...


```
HttpServletRequest request = ...

FrtjServletUrl result = FrtjServletUrlFactory.toUrl(request);
```

#### Rationale:
Maybe there is other, and better, out there that does a better job. I just made one.

It can parse partial url like, only the path part, but for the most part the url
must be a valid url. But not all invalid url is considered illegal.

Read the test, there are many examples there.


#### Todo:
- printing
- normalizing
- option to change default parsers/printers
- upload artifact to some maven repo
