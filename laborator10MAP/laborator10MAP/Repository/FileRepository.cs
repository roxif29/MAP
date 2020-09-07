using laborator10MAP.domain;
using laborator10MAP.validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.repository
{
    public abstract class FileRepository<ID, E> : ICrudRepository<ID, E> where E : Entity<ID>
    {

        private Dictionary<ID, E> dictionar;
        private string filePath;
        private IValidator<E> validator;

        protected abstract E ReadEntity(string line);
        protected abstract string WriteEntity(E entity);

        public FileRepository(IValidator<E> Validator, string FilePath)
        {
            this.dictionar = new Dictionary<ID, E>();
            this.filePath = FilePath;
            this.validator = Validator;
        }



        public E FindOne(ID id)
        {
            if (id == null)
                throw new RepositoryException("Id ul introdus este null!");
            if (dictionar.ContainsKey(id))
                return dictionar[id];
            return null;

        }

        public IEnumerable<E> FindAll()
        {
            return dictionar.Values;
        }

        public E Save(E entity)
        {
            if (entity == null)
                throw new RepositoryException("Entitatea este nula");
            validator.Validate(entity);
            if (dictionar.ContainsKey(entity.Id))
                return entity;
            dictionar.Add(entity.Id, entity);
            WriteAllToFile();
            return null;

        }

        public E Delete(ID id)
        {
            if (id == null)
                throw new RepositoryException("Id ul introdus este null!");
            if (!dictionar.ContainsKey(id))
                return null;
            E entity = dictionar[id];
            dictionar.Remove(id);
            WriteAllToFile();
            return entity;
        }

        public E Update(E entity)
        {
            if (entity == null)
                throw new RepositoryException("Entitatea  este nula");
            validator.Validate(entity);
            if (!dictionar.ContainsKey(entity.Id))
                return entity;
            dictionar[entity.Id] = entity;
            WriteAllToFile();
            return null;
        }

        public void ReadAllFromFile()
        {
            if (System.IO.File.Exists(filePath))
            {
                System.IO.StreamReader file = new System.IO.StreamReader(filePath);
                while (!file.EndOfStream)
                {
                    E entity = ReadEntity(file.ReadLine());
                    dictionar.Add(entity.Id, entity);
                }
                file.Close();
            }
        }

        public void WriteAllToFile()
        {
            using (System.IO.StreamWriter file = new System.IO.StreamWriter(filePath))
            {
                foreach (E entity in dictionar.Values)
                    file.WriteLine(WriteEntity(entity));
            }
        }

    }
}
