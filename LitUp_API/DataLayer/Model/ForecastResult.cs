using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class ForecastResult
    {
        public ForecastResult()
        {
            List = new List<WeatherResult>();
        }
        public int Cnt { get; set; }
        public List<WeatherResult> List { get; set; }

    }
}
