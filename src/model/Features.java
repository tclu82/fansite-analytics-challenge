package model;

import control.ReadFile;

/**
 * Created by zac on 4/2/17.
 */
public abstract class Features {

    protected ReadFile dataStruct;

    protected Features(ReadFile dataStruct) {
        this.dataStruct = dataStruct;
    }

    public abstract void execute();
}
