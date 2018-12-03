using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DataLayer
{
    public class DbInitializer
    {
        public static void Initialize(LitUpContext context)
        {
            context.Database.EnsureCreated();

            if(!context.Settings.Any())
            {
                Settings s1 = new Settings()
                {
                    Brightness = 50,
                    Wake_SleepTime = { new TimeSpan(10, 30, 00), new TimeSpan(11, 00, 00) },
                    Location = "Antwerp",
                    Unit = 'C'
                };

                context.Settings.Add(s1);
                context.SaveChanges();
            }
        }
    }
}
