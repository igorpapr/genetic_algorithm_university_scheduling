package utils;

import java.util.Random;

public class ScheduleUtils {

	/*
	 * Returns random integer number between min and max ( all included :)  )
	 * */
	public static int getRandomInt( int min, int max ){
		Random randomGenerator;
		randomGenerator = new Random();
		return  randomGenerator.nextInt( max+1 ) + min ;
	}

	/*
	 * Returns random float number between min (included) and max ( NOT included :)  )
	 * */
	public static float getRandomFloat( float min, float max ){
		return  (float) (Math.random()*max + min) ;
	}

}
