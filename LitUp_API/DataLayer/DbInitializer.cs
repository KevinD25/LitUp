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
                    Wake_SleepTime = { 10,11 },
                    Location = "Antwerp",
                    Unit = 'C'
                };

                context.Settings.Add(s1);
                context.SaveChanges();
            }
        }
    }
}
