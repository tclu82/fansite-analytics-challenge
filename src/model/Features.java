package model;

import control.ReadFile;

/**
 * Created by zac on 4/2/17.
 *
 * This Abstract class is the parent class of all features, contains input file then passes to its children
 */
public abstract class Features {
    /** Load data from ReadFile */
    protected ReadFile readFile;

    /**
     * Constructor
     *
     * @param readFile
     */
    protected Features(ReadFile readFile)
    {
        this.readFile = readFile;
    }

    /**
     * Abstract method
     */
    public abstract void execute();
}
