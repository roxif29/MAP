
using laborator10MAP.domain;
using laborator10MAP.validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.repository
{
   
    public class EchipaFileRepository : FileRepository<int, Echipa>
    {
        public EchipaFileRepository(IValidator<Echipa> Validator, string FilePath) : base(Validator, FilePath)
        {
//            ReadAllFromFile();
        }

        protected override Echipa ReadEntity(string line)
        {
            string[] fields = line.Split(new[] {","}, StringSplitOptions.RemoveEmptyEntries);
            return new Echipa(int.Parse(fields[0]), fields[1]);
        }

        protected override string WriteEntity(Echipa entity)
        {
            return entity.Id + "," + entity.Nume;
        }
    }
}
