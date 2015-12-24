package com.example.hsienyu.lab2;
import java.util.*;
public class WholeData {
	private static HashMap<String, Object> data = new HashMap<String, Object>();
	public WholeData() {
		data.put("setting_water", 50);
		data.put("setting_air", 20);
		data.put("setting_ph", 7.0);
		data.put("user_name1", "xxx");
		data.put("password1", "1234");
		data.put("plant1_water", 20);
		data.put("plant1_air", 40);
		data.put("plant3_ph", 10);
		data.put("feed", false);
		data.put("plant1_ph", 5);
		data.put("plant1_pname", "bullshit");
	}
	public static HashMap<String, Object> getHashMap() {
		return data;
	}
	public static int getState(int num){
		String name = "plant" + Integer.toString(num);
		int water = (int)data.get(name + "_water");
		int air = (int)data.get(name + "_air");
		int ph = (int)data.get(name + "_ph");
		int bullshit = ((water > 10)?2:1) + ((air >= 30)?2:1) + ((ph == 7)?2:1);
		return (bullshit > 3)?2:1;
	}
}

