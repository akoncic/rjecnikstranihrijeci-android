package com.dekoraktiv.android.rsr.models;

import java.io.Serializable;

public class Dictionary implements Serializable {
    private String id;
    private String grammar;
    private String definition;
    private String phrase;
    private String phraseology;
    private String onomastics;
    private String etymology;

    public String getId() {
        return id;
    }

    public String getGrammar() {
        return grammar;
    }

    public String getDefinition() {
        return definition;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getPhraseology() {
        return phraseology;
    }

    public String getOnomastics() {
        return onomastics;
    }

    public String getEtymology() {
        return etymology;
    }
}
