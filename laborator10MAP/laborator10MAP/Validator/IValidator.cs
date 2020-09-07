using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.validator
{
    public interface IValidator<E>
    {
        void Validate(E entity);
    }
}
