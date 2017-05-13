package com.dekoraktiv.android.rsr.models;

import java.io.Serializable;

public class Api implements Serializable {
    private int status;
    private int version;

    public int getStatus() {
        return status;
    }

    public int getVersion() {
        return version;
    }
}
