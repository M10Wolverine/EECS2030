import java.util.ArrayList;

/**
 * A weather statistics app calculates the minimum, maximum, and average
 * temperature read so far from its subscribed weather station, if any.
 */
public class StatisticsApp extends WeatherObserver
{

	WeatherStation subbedWS;
	double minTemp = Double.MAX_VALUE;
	double maxTemp = Double.MIN_VALUE;
	double numUpdates = 0;
	ArrayList<Double> tempData = new ArrayList<Double>();

	/**
	 * Update the reading of this weather observer. Update the current temperature
	 * and the maximum, minimum, and average accordingly.
	 */
	public void update()
	{
		/* Your Task */
		tempData.add(subbedWS.getTemperature());
		numUpdates++;

		this.getAverageTemperature();
		this.getMaxTemperature();
		this.getMinTemperature();
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
	 * Get the minimum temperature based on the readings so far.
	 * 
	 * @return minimum temperature based on the readings so far
	 */
	public double getMinTemperature()
	{
		/* Your Task */
		if (tempData.get((int) numUpdates - 1) < minTemp)
		{
			minTemp = tempData.get((int) numUpdates - 1);
		}

		return minTemp;
	}

	/**
	 * Get the maximum temperature based on the readings so far.
	 * 
	 * @return maximum temperature based on the readings so far
	 */
	public double getMaxTemperature()
	{
		/* Your Task */

		if (tempData.get((int) numUpdates - 1) > maxTemp)
		{
			maxTemp = tempData.get((int) numUpdates - 1);
		}

		return maxTemp;
	}

	/**
	 * Get the average temperature based on the readings so far.
	 * 
	 * @return average temperature based on the readings so far
	 */
	public double getAverageTemperature()
	{
		/* Your Task */
		double avTemp = 0;
		for (double d : tempData)
		{
			avTemp += d;
		}

		return avTemp / numUpdates;
	}
}
