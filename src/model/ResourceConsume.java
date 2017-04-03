package model;

/**
 * Created by zac on 4/1/17.
 */
public class ResourceConsume {
    String resourceName;
    int resourceSize;
    int frequency;

    public ResourceConsume(String name, int size) {
        this.resourceName = name;
        this.resourceSize = size;
        frequency = 0;
    }

    public void addFrequency() {
        this.frequency++;
    }
}
