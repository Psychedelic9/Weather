package com.psychedelic.weather.entity

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/14 9:38
 *@UpdateDate: 2019/5/14 9:38
 *@Description:
 *@ClassName: WeatherBean
 */
class WeatherBean {


    var HeWeather: ArrayList<HeWeatherBean>? = null

    class HeWeatherBean {
        var basic: BasicBean? = null
        var now: NowBean? = null
        var status: String? = null
        var suggestion: SuggestionBean? = null
        var daily_forecast: ArrayList<DailyForecastBean>? = null
        var hourly_forecast: ArrayList<HourlyForecastBean>? = null

        class BasicBean {
            var city: String? = null
            var cnty: String? = null
            var id: String? = null
            var lat: String? = null
            var lon: String? = null
            var update: UpdateBean? = null

            class UpdateBean {
                var loc: String? = null
                var utc: String? = null
            }
        }

        class NowBean {
            var cond: CondBean? = null
            var fl: String? = null
            var hum: String? = null
            var pcpn: String? = null
            var pres: String? = null
            var tmp: Int = 0
            var vis: String? = null
            var wind: WindBean? = null

            class CondBean {
                var code: String? = null
                var txt: String? = null
            }

            class WindBean {
                var deg: String? = null
                var dir: String? = null
                var sc: String? = null
                var spd: String? = null
            }
        }

        class SuggestionBean {
            var comf: ComfBean? = null
            var cw: CwBean? = null
            var drsg: DrsgBean? = null
            var flu: FluBean? = null
            var sport: SportBean? = null
            var trav: TravBean? = null
            var uv: UvBean? = null

            class ComfBean {

                var brf: String? = null
                var txt: String? = null
            }

            class CwBean {

                var brf: String? = null
                var txt: String? = null
            }

            class DrsgBean {

                var brf: String? = null
                var txt: String? = null
            }

            class FluBean {

                var brf: String? = null
                var txt: String? = null
            }

            class SportBean {


                var brf: String? = null
                var txt: String? = null
            }

            class TravBean {


                var brf: String? = null
                var txt: String? = null
            }

            class UvBean {


                var brf: String? = null
                var txt: String? = null
            }
        }

        class DailyForecastBean {

            var astro: AstroBean? = null
            var cond: CondBeanX? = null
            var date: String? = null
            var hum: String? = null
            var pcpn: String? = null
            var pop: String? = null
            var pres: String? = null
            var tmp: TmpBean? = null
            var uv: String? = null
            var vis: String? = null
            var wind: WindBeanX? = null

            class AstroBean {

                var mr: String? = null
                var ms: String? = null
                var sr: String? = null
                var ss: String? = null
            }

            class CondBeanX {

                var code_d: String? = null
                var code_n: String? = null
                var txt_d: String? = null
                var txt_n: String? = null
            }

            class TmpBean {

                var max: String? = null
                var min: String? = null
            }

            class WindBeanX {

                var deg: String? = null
                var dir: String? = null
                var sc: String? = null
                var spd: String? = null
            }
        }

        class HourlyForecastBean {

            var cond: CondBeanXX? = null
            var date: String? = null
            var hum: String? = null
            var pop: String? = null
            var pres: String? = null
            var tmp: String? = null
            var wind: WindBeanXX? = null

            class CondBeanXX {

                var code: String? = null
                var txt: String? = null
            }

            class WindBeanXX {


                var deg: String? = null
                var dir: String? = null
                var sc: String? = null
                var spd: String? = null
            }
        }
    }
}