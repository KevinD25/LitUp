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
                    WakeTime = "10:30",
                    SleepTime = "11:00",
                    Location = "Antwerp",
                    Unit = 'C'
                };

                context.Settings.Add(s1);
                context.SaveChanges();
            }

            if (!context.Users.Any())
            {
                Settings s = new Settings()
                {
                    DeviceName = "Litup",
                    Brightness = 65,
                    WakeTime = "8:00",
                    SleepTime = "22:00",
                    Location = "Antwerp",
                    Unit = 'C'
                };

                User u = new User()
                {
                    FirebaseId = "c9v8uwZaKKXtY1r87UFlQkoOKB53",
                    PersonalSettings = s
                };

                context.Settings.Add(s);
                context.Users.Add(u);
                context.SaveChanges();
            }
        }
    }
}
