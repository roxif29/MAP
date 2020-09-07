using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.validator
{
    public class ValidationException : Exception
    {
        public ValidationException()
        {
        }

        public ValidationException(string mesaj) : base(mesaj)
        {
        }
    }
}
