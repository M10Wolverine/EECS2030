import java.util.ArrayList;

/**
 * A weather forecast app determines if the pressure readings from its
 * subscribed weather station, if any, indicate it is likely to rain due to a
 * reduction on the pressure level.
 */
public class ForecastApp extends WeatherObserver
{

	WeatherStation subbedWS;

	ArrayList<Double> pressList = new ArrayList<Double>();
	double numUpdates = 0;

	/**
	 * Update the reading of this weather observer. Update the current and last
	 * readings of pressure.
	 */
	public void update()
	{
		/* Your Task */
		pressList.add(subbedWS.getPressure());
		numUpdates++;

	}

	/* See the method description in the parent class */
	public WeatherStation getWeatherStation()
	{
		/* Your Task */
		return this.subbedWS;
	}

	/* See the method description in the parent class */
	public void setWeatherStation(WeatherStation ws)
	{
		/* Your Task */
		subbedWS = ws;
		if (ws != null)
		{
			ws.subList.add(this);
		}
	}

	/**
	 * Get the pressure level read from the last update.
	 * 
	 * @return pressure level read from the last update
	 */
	public double getCurrentPressure()
	{
		/* Your Task */
		return pressList.get((int) numUpdates - 1);
	}

	/**
	 * Get the pressure level read from the second last update.
	 * 
	 * @return pressure level read from the second last update
	 */
	public double getLastPressure()
	{
		/* Your Task */
		if (numUpdates == 1)
		{
			return getCurrentPressure();
		}
		return pressList.get((int) numUpdates - 2);
	}

	/**
	 * Based on the last and second last readings of the pressure level, it is
	 * determined as likely to rain if there is a reduction on the pressure level;
	 * otherwise it is unlikely to rain.
	 * 
	 * @return whether or not it is likely to rain.
	 */
	public boolean isLikelyToRain()
	{
		/* Your Task */
		if (this.getCurrentPressure() < this.getLastPressure())
		{
			return true;
		}
		return false;
	}
}