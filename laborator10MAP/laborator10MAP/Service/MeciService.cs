using laborator10MAP.domain;
using laborator10MAP.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace laborator10MAP.service
{
    public class MeciService : Service<int, Meci>
    {
        public MeciService(FileRepository<int, Meci> repository) : base(repository)
        {
        }

        public IEnumerable<Meci> ToateMeciurileDintr_oPerioada(DateTime dateTime1, DateTime dateTime2)
        {
            return Repository.FindAll()
                .Where(x => x.DateTime <= dateTime1 && x.DateTime >= dateTime2);
        }

    }
}
