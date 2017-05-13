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

    public void setId(String id) {
        this.id = id;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhraseology() {
        return phraseology;
    }

    public void setPhraseology(String phraseology) {
        this.phraseology = phraseology;
    }

    public String getOnomastics() {
        return onomastics;
    }

    public void setOnomastics(String onomastics) {
        this.onomastics = onomastics;
    }

    public String getEtymology() {
        return etymology;
    }

    public void setEtymology(String etymology) {
        this.etymology = etymology;
    }
}
