using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace DataLayer.Model
{
    public class Settings
    {
        public Settings()
        {
            Brightness = 50;
            WakeTime = "8:00:00";
            SleepTime = "22:00:00";
            Location = "Antwerp";
            Unit = 'C';
        }
        public int Id { get; set; }
        public int Brightness { get; set; }
        public string WakeTime { get; set; }
        public string SleepTime { get; set; }
        public string Location { get; set; }
        public char Unit { get; set; }


    }
}
