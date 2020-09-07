package Repository;

import Domain.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * CRUD operations repository interface
 * @param <ID> - type E must have an attribute of type ID
 * @param <E> - type of map saved in repository
 */
public abstract class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID,E> {





    Map<ID, E> map;

    public InMemoryRepository(){

        map = new HashMap<ID,E>();
    }

    @Override
    public E save (E element){
        if (element==null)
            throw new IllegalArgumentException("element must not be null");
        if(map.get(element.getId() ) != null) {
            return element;
        }
        map.put(element.getId(),element);
        return null;
    }

    @Override
    public E findOne(ID id){
        if (id==null)
            throw new IllegalArgumentException("id must be not null");
        return map.get(id);
    }

    @Override
    public E update (E newElement){
        if(newElement == null)
            throw new IllegalArgumentException("entity must be not null!");
        ID id=newElement.getId();
        if(map.containsKey(id)) {
            map.replace(id, newElement);
        }
        return newElement;
    }

    @Override
    public E delete(ID id){
        E element = map.get(id);
        if (element != null)
            map.remove(id);
        return element;
    }

    @Override
    public Iterable <E> findAll(){
        return map.values();
    }

}

