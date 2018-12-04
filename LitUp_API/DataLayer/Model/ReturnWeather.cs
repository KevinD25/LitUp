using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class ReturnWeather
    {
        public int Time { get; set; }
        public double Temp { get; set; }
        public string Weather { get; set; }
        public string WeatherDetail { get; set; }
    }
}
