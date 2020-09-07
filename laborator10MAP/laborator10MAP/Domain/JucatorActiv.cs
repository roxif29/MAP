using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.domain
{
    public class JucatorActiv : Jucator
    {

        public Tip Tip { get; set; }
        public int IdMeci { get; set; }
        public int NrPuncteInscrise { get; set; }

        public JucatorActiv(int id, string Nume, string Scoala, Echipa Echipa,
            int IdMeci, int NrPuncteInscrise, Tip Tip) : base(id, Nume, Scoala, Echipa)
        {
            this.Tip = Tip;
            this.IdMeci = IdMeci;
            this.NrPuncteInscrise = NrPuncteInscrise;
        }

        public override string ToString()
        {
            return base.ToString() + " " + this.Tip + " " + this.IdMeci + " " + this.NrPuncteInscrise;
        }
    }
}
