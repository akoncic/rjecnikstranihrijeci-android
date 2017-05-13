package com.dekoraktiv.android.rsr.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Stem implements Serializable {
    @SerializedName("dictionary")
    private List<Dictionary> dictionaries;

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }
}
