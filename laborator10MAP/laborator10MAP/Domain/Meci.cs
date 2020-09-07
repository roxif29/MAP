using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.domain
{
    public class Meci : Entity<int>
    {
        public Echipa Echipa1 { get; set; }
        public Echipa Echipa2 { get; set; }

        public DateTime DateTime { get; set; }

        public Meci(int Id, Echipa Echipa1, Echipa Echipa2, DateTime DateTime) : base(Id)
        {
            this.Echipa1 = Echipa1;
            this.Echipa2 = Echipa2;
            this.DateTime = DateTime;
        }

        public override string ToString()
        {
            return base.ToString() + " " + this.Echipa1 + this.Echipa2 + this.DateTime;
        }
    }
}
