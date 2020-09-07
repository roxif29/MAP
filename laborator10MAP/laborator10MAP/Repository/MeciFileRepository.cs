using laborator10MAP.domain;
using laborator10MAP.validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.repository
{
    public class MeciFileRepository : FileRepository<int, Meci>
    {
        private FileRepository<int, Echipa> repoEchipa;

        public MeciFileRepository(IValidator<Meci> Validator, string FilePath,
            FileRepository<int, Echipa> repoEchipa) : base(Validator, FilePath)
        {
            this.repoEchipa = repoEchipa;
            //ReadAllFromFile();
        }

        protected override Meci ReadEntity(string line)
        {

            string[] fields = line.Split(new[] { "," }, StringSplitOptions.RemoveEmptyEntries);
            Echipa Echipa1 = repoEchipa.FindOne(int.Parse(fields[1]));
            Echipa Echipa2 = repoEchipa.FindOne(int.Parse(fields[2]));
            return new Meci(int.Parse(fields[0]), Echipa1, Echipa2, DateTime.Parse(fields[3]));
        }

        protected override string WriteEntity(Meci entity)
        {
            return entity.Id + "," + entity.Echipa1 + "," + entity.Echipa2 + "," + entity.DateTime;
        }
    }
}
