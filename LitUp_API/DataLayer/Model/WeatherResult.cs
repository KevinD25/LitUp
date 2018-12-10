using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class WeatherResult
    {
        public WeatherResult()
        {
            Weather = new List<WeatherDescription>();
        }
        public int Dt { get; set; }
        public WeatherMain Main { get; set; }
        public List<WeatherDescription> Weather { get; set; }

    }
}
