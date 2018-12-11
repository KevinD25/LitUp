using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Text;

namespace BusinessLayer
{
    public class UserService
    {
        private readonly LitUpContext litUpContext;

        public UserService(LitUpContext litUpContext)
        {
            this.litUpContext = litUpContext;
        }

        public Settings getSettings(int id)
        {
            return litUpContext.Settings.Find(id);
        }

        public Settings UpdateSettings(int id, Settings newSettings)
        {
            if (id == 0)
                return newSettings(newSettings);
            Settings s_old = litUpContext.Settings.Find(id);
            if (s_old != null)
            {
                if (newSettings.Brightness != 0)
                    s_old.Brightness = newSettings.Brightness;
                if (newSettings.Location != null || newSettings.Location != "")
                    s_old.Location = newSettings.Location;
                if (newSettings.Unit == 'C' || newSettings.Unit == 'F')
                    s_old.Unit = newSettings.Unit;
                if (newSettings.Wake_SleepTime != null || newSettings.Wake_SleepTime != "")
                    s_old.Wake_SleepTime = newSettings.Wake_SleepTime;
                litUpContext.SaveChanges();
                return s_old;
            }
            else return null;
        }

        public Settings newSettings(Settings settings)
        {
            litUpContext.Settings.Add(settings);
            litUpContext.SaveChanges();
            return litUpContext.Settings.Find(settings);
        }
    }
}
