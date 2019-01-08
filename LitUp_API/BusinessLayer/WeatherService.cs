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

        public async Task<ReturnData> forecastAntwerp(string location = "antwerp")
        {
            using (var client = new HttpClient())
            {
                try
                {
                    client.BaseAddress = new Uri("http://api.openweathermap.org");
                    var response = await client.GetAsync($"/data/2.5/forecast?q={location}&cnt={count}&appid={apiKey}");
                    response.EnsureSuccessStatusCode();
                    var stringResult = await response.Content.ReadAsStringAsync();
                    var forecast = getData(stringResult);
                    
                    return forecast;
                }catch (HttpRequestException e)
                {
                    return null;
                }
            }
        }
        public ReturnData getData(string stringResult)
        {
            try
            {
                var forecast = JsonConvert.DeserializeObject<ForecastResult>(stringResult);
                ReturnData returnData = new ReturnData();
                forecast.List.ForEach(item => {
                    returnData.List.Add(new ReturnWeather { Time = item.Dt,Temp = item.Main.Temp, Weather = item.Weather[0].Main, WeatherDetail = item.Weather[0].Description });
                });
                returnData.Count = forecast.Cnt;

                return returnData;
            }catch(Exception e)
            {
                return null;
            }
        }
    }
}
