package Domain;

import java.io.Serializable;

/**
 *
 * @param <ID> - generic type for an unique identifier for the entity
 *
 */
public class Entity<ID> implements Serializable {

    private static final long serialVersionUID = 7331115341259248461L;
    private ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }

}