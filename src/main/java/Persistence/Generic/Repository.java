package Persistence.Generic;

import Domain.Entity;

public interface Repository<ID, E extends Entity<ID>> {
    E Save(E e);
    E Remove(ID id);
    E Update(ID id,E newer);
    E FindById(ID id);
    Iterable<E> GetAll();
    Long Count();
}
