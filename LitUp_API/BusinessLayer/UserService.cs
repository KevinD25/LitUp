using DataLayer;
using System;
using System.Collections.Generic;
using System.Text;

namespace BusinessLayer
{
    public class UserService
    {
        private readonly LitUpContext litUpContext;

        public UserService(LitUpContext litUpContext)
        {
            this.litUpContext = litUpContext;
        }
    }
}
