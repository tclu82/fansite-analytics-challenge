package zac.com;

/**
 * Created by zac on 4/1/17.
 */
public class ResourceConsume {
    String resourceName;
    int resourceSize;
    int frequency;

    ResourceConsume(String name, int size) {
        this.resourceName = name;
        this.resourceSize = size;
        frequency = 0;
    }

    void addFrequency() {
        this.frequency++;
    }
}
