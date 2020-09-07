using laborator10MAP.domain;
using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.repository
{
    public interface ICrudRepository<ID, E> where E : Entity<ID>
    {

        E FindOne(ID id);
        IEnumerable<E> FindAll();

        E Save(E entity);

        E Delete(ID id);

        E Update(E entity);
    }
}
