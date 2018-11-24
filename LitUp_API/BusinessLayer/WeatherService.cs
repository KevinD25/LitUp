using DataLayer;
using System;
using System.Collections.Generic;
using System.Text;

namespace BusinessLayer
{
    public class WeatherService
    {
        private readonly LitUpContext context;

        public WeatherService(LitUpContext context)
        {
            this.context = context;
        }
    }
}
