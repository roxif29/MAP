using laborator10MAP.domain;
using laborator10MAP.validator;
using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.repository
{
    public class JucatorActivFileRepository : FileRepository<int, JucatorActiv>
    {
        private FileRepository<int, Echipa> repoEchipa;
        public JucatorActivFileRepository(IValidator<JucatorActiv> Validator, string FilePath,
            FileRepository<int, Echipa> repoEchipa) : base(Validator, FilePath)
        {
            this.repoEchipa = repoEchipa;
        }

        protected override JucatorActiv ReadEntity(string line)
        {

            string[] fields = line.Split(new[] { "," }, StringSplitOptions.RemoveEmptyEntries);
            Echipa Echipa = repoEchipa.FindOne(int.Parse(fields[3]));

            return new JucatorActiv(int.Parse(fields[0]), fields[1], fields[2], Echipa, int.Parse(fields[5]), int.Parse(fields[6]),
                (Tip)Enum.Parse(typeof(Tip), fields[4]));
        }

        protected override string WriteEntity(JucatorActiv entity)
        {
            return entity.Id + "," + entity.Nume + "," + entity.Scoala + "," + entity.Echipa + "," + entity.Tip + "," + entity.IdMeci + "," +
                   entity.NrPuncteInscrise;
        }
    }
}
