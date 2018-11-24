using BusinessLayer;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace LitUp_API.Controllers
{
    [Produces("application/json")]
    [Route("api/weather")]
    public class WeatherController : Controller
    {
        private readonly WeatherService weatherService;

        public WeatherController(WeatherService weatherService)
        {
            this.weatherService = weatherService;
        }

        [HttpGet]
        public IActionResult Weather()
        {
            return Ok("test");
        }
    }
}
