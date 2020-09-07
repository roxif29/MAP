using laborator10MAP.domain;
using laborator10MAP.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace laborator10MAP.service
{
    public class JucatorActivService : Service<int, JucatorActiv>
    {

        public JucatorActivService(FileRepository<int, JucatorActiv> repository
            ) : base(repository)
        {
        }

        public IEnumerable<Jucator> TotiJucatoriiActiviDeLaOEchipaDeLaUnMeci(int EchipaId, int MeciId)
        {
            return Repository.FindAll()
                .Where(x => x.IdMeci.Equals(MeciId) && x.Echipa.Id.Equals(EchipaId));

        }


    }
}
