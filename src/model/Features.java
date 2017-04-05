package model;

import control.ReadFile;

/**
 * Created by zac on 4/2/17.
 */
public abstract class Features {

    protected ReadFile readFile;

    protected Features(ReadFile readFile) {
        this.readFile = readFile;
    }

    public abstract void execute();
}
