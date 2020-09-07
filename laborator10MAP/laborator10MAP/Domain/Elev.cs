using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.domain
{
    public class Elev : Entity<int>
    {
        public string Nume { get; set; }
        public string Scoala { get; set; }

        public Elev(int Id, string Nume, string Scoala) : base(Id)
        {
            this.Nume = Nume;
            this.Scoala = Scoala;
        }

        public override string ToString()
        {
            return base.ToString() + " " + this.Nume + " " + this.Scoala;
        }
    }
}
