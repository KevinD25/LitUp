using DataLayer.Model;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer
{
    public class LitUpContext : DbContext
    {
        public LitUpContext(DbContextOptions<LitUpContext> options) : base(options)
        {

        }

        public DbSet<Settings> Settings { get; set; }
        public DbSet<User> Users { get; set; }
    }
}
