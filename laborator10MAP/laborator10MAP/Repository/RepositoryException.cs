using System;
using System.Collections.Generic;
using System.Text;

namespace laborator10MAP.repository
{
    public class RepositoryException : Exception
    {
        public RepositoryException()
        {
        }

        public RepositoryException(string message) : base(message)
        {
        }

    }
}
