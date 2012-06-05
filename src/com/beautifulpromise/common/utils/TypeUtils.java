package com.beautifulpromise.common.utils;

public class TypeUtils {
	
	public static int BooleanToInteger(boolean bData){
		return (bData) ? 1 : 0;  // true = 1 , false = 0 
	}
	
	public static boolean IntegerToBoolean(int value){
		return (value==1) ? true : false;  // true = 1 , false = 0 
	}
}
