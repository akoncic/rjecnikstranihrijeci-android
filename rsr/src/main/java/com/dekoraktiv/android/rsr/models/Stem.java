package com.dekoraktiv.android.rsr.models;

import java.io.Serializable;
import java.util.List;

public class Stem implements Serializable {
    private List<Dictionary> dictionaries;

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(List<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }
}
