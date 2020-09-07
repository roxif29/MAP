using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.domain
{
    public class Echipa : Entity<int>
    {

        public string Nume { get; set; }

        public Echipa(int id, string Nume) : base(id)
        {
            this.Nume = Nume;
        }

        public override string ToString()
        {
            return base.ToString() + " " + this.Nume;
        }
    }
}
