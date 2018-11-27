using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class WeatherResult
    {
        public int Dt { get; set; }
        public WeatherMain Main { get; set; }
        public IEnumerable<WeatherDescription> Weather { get; set; }

    }
}
