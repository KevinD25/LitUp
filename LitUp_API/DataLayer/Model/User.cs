using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model
{
    public class User
    {
        public User()
        {
            PersonalSettings = new Settings();
        }

        public User(string firebaseId)
        {
            PersonalSettings = new Settings();
            FirebaseId = firebaseId;
        }
        public int Id { get; set; }
        public string FirebaseId { get; set; }
        public Settings PersonalSettings { get; set; }

    }
}
