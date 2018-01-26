package cache;

import objects.types.EntitiesTypes;

/**
 * @author P.Pridorozhny
 */
public interface Cacheable {
    EntitiesTypes getType();
    long getId();
}
