package Repository;

import Domain.Entity;

public interface CrudRepository <ID,E extends Entity<ID>> {
    public E save (E element);
    public E findOne(ID id);
    public E update (E newElement);
    public E delete (ID id);
    public Iterable<E> findAll();
}
