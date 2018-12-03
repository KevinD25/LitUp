using BusinessLayer;
using DataLayer.Model;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LitUp_API.Controllers
{
    [Produces("application/json")]
    [Route("api/user")]
    public class UserController : Controller
    {
        private readonly UserService userService;

        public UserController(UserService userService)
        {
            this.userService = userService;
        }


        [Route("settings/{id}")]
        [HttpGet]
        public IActionResult settings(int id)
        {
            Settings settings = userService.getSettings(id);
            if (settings != null)
            {
                return Ok(settings);
            }
            else return NotFound();
        }
    }
}
