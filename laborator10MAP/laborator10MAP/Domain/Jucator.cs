using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.domain
{
    public class Jucator : Elev
    {
        public Echipa Echipa { get; set; }

        public Jucator(int Id, string Nume, string Scoala, Echipa Echipa) : base(Id, Nume, Scoala)
        {
            this.Echipa = Echipa;
        }

        public override string ToString()
        {
            return base.ToString() + " " + this.Echipa;
        }
    }
}
