using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class ReturnData
    {
        public ReturnData()
        {
            List = new List<ReturnWeather>();
        }
        public int Count { get; set; }
        public List<ReturnWeather> List { get; set; }

    }
}
