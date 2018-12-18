using BusinessLayer;
using DataLayer.Model;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LitUp_API.Controllers
{
    [Authorize]
    [Produces("application/json")]
    [Route("api/user")]
    public class UserController : Controller
    {
        private readonly UserService userService;

        public UserController(UserService userService)
        {
            this.userService = userService;
        }

        //[Route("{id}")]
        [HttpGet]
        public IActionResult GetUser([FromHeader] string FirebaseId)
        {
            User user = userService.getUser(FirebaseId);
            if (user != null)
                if(user.Id != 0)
                    return Ok(user);
                else return NotFound("User does not exist");
            return NotFound("FirebaseId is not valid");
        }

        [Route("settings/{id}")]
        [HttpGet]
        public IActionResult Settings(int id)
        {
            Settings settings = userService.getSettings(id);
            if (settings != null)
            {
                return Ok(settings);
            }
            else return NotFound();
        }

        [Route("settings/{id}")]
        [HttpPut]
        public IActionResult Settings(int id, [FromBody] Settings newSettings)
        {
            Settings settings = userService.UpdateSettings(id, newSettings);
            if (settings != null)
                return Ok();
            return NotFound();
        }
    }
}
