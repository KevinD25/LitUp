using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class ForecastResult
    {
        public int Cnt { get; set; }
        public IEnumerable<WeatherResult> List { get; set; }
    }
}
