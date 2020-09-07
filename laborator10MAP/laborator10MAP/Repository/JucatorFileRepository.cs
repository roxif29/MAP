using laborator10MAP.domain;
using laborator10MAP.repository;
using laborator10MAP.validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.Repository
{
    public class JucatorFileRepository : FileRepository<int, Jucator>
    {
        private FileRepository<int, Echipa> repoEchipa;

        public JucatorFileRepository(IValidator<Jucator> Validator, string FilePath,
            FileRepository<int, Echipa> repoEchipa) : base(Validator, FilePath)
        {
            this.repoEchipa = repoEchipa;
            //ReadAllFromFile();
        }

        protected override Jucator ReadEntity(string line)
        {
            string[] fields = line.Split(new[] { "," }, StringSplitOptions.RemoveEmptyEntries);
            Echipa Echipa = repoEchipa.FindOne(int.Parse(fields[3]));
            return new Jucator(int.Parse(fields[0]), fields[1], fields[2], Echipa);
        }

        protected override string WriteEntity(Jucator entity)
        {
            return entity.Id + "," + entity.Nume + "," + entity.Scoala + "," + entity.Echipa;
        }
    }
}
