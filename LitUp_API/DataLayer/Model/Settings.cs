using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class Settings
    {
        public int Brightness { get; set; }
        public ICollection<int> Wake_SleepTime { get; set; }
        public string Location { get; set; }
        public char Unit { get; set; }


    }
}
