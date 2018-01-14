package no.frtj.frtjurl;

import java.util.List;

public class FrtjUrlQuery {
    public List<FrtjUrlQueryElem> queryElems;

    public FrtjUrlQueryElem get(int i) {
        return queryElems.get(i);
    }
}
