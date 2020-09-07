using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.domain
{
    public class Entity<ID>
    {
        public Entity(ID id)
        {
            Id = id;
        }

        public ID Id { get; set; }

        public override string ToString()
        {
            return $"{nameof(Id)}: {Id}";
        }

        protected bool Equals(Entity<ID> other)
        {
            return EqualityComparer<ID>.Default.Equals(Id, other.Id);
        }

        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            if (obj.GetType() != this.GetType()) return false;
            return Equals((Entity<ID>)obj);
        }

        public override int GetHashCode()
        {
            return EqualityComparer<ID>.Default.GetHashCode(Id);
        }
    }
}
