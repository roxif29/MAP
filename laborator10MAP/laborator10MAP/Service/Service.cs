using laborator10MAP.domain;
using laborator10MAP.repository;
using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.service
{
    public class Service<ID, E> where E : Entity<ID>
    {
        protected FileRepository<ID, E> Repository;

        public Service(FileRepository<ID, E> repository)
        {
            Repository = repository;
        }

        public E FindOne(ID id)
        {
            return Repository.FindOne(id);

        }

        public IEnumerable<E> FindAll()
        {
            return Repository.FindAll();
        }

        public E Save(E entity)
        {
            return Repository.Save(entity);
        }

        public E Delete(ID id)
        {
            return Repository.Delete(id);
        }

        public E Update(E entity)
        {
            return Repository.Update(entity);
        }

        public void WriteAllToFile()
        {
            Repository.WriteAllToFile();
        }
    }
}
