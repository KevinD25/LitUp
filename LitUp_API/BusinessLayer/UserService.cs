using DataLayer;
using DataLayer.Model;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
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
                return NewSettings(newSettings);
            Settings s_old = litUpContext.Settings.Find(id);
            if (s_old != null)
            {
                if (newSettings.Brightness != 0)
                    s_old.Brightness = newSettings.Brightness;
                if (newSettings.Location != null || newSettings.Location != "")
                    s_old.Location = newSettings.Location;
                if (newSettings.Unit == 'C' || newSettings.Unit == 'F')
                    s_old.Unit = newSettings.Unit;
                if (newSettings.WakeTime != null || newSettings.WakeTime != "")
                    s_old.WakeTime = newSettings.WakeTime;
                if (newSettings.SleepTime != null || newSettings.SleepTime != "")
                    s_old.SleepTime = newSettings.SleepTime;
                litUpContext.SaveChanges();
                return s_old;
            }
            else return null;
        }

        public Settings NewSettings(Settings settings)
        {
            litUpContext.Settings.Add(settings);
            litUpContext.SaveChanges();
            return litUpContext.Settings.Find(settings);
        }

        public User getUser(int id)
        {
            return litUpContext.Users.Include(u => u.PersonalSettings).SingleOrDefault(u => u.Id == id);
        }
    }
}
