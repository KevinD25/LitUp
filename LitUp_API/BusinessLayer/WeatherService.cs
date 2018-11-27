using DataLayer;
using DataLayer.Model;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace BusinessLayer
{
    public class WeatherService
    {
        private readonly LitUpContext context;
        private string apiKey = "2c165f1b7fbd650cf068827adc8eb952";
        private int count = 4;

        public WeatherService(LitUpContext context)
        {
            this.context = context;
        }

        public async Task<ForecastResult> forecastAntwerp()
        {
            using (var client = new HttpClient())
            {
                try
                {
                    client.BaseAddress = new Uri("http://api.openweathermap.org");
                    var response = await client.GetAsync($"/data/2.5/forecast?q=antwerp&cnt={count}&appid={apiKey}");
                    response.EnsureSuccessStatusCode();
                    var stringResult = await response.Content.ReadAsStringAsync();
                    var forecast = JsonConvert.DeserializeObject<ForecastResult>(stringResult);
                    
                    return forecast;
                }catch (HttpRequestException e)
                {
                    return null;
                }
            }
        }
    }
}
