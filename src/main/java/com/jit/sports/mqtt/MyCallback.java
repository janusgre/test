package com.jit.sports.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jit.sports.InfluxDB.InfluxDealData;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyCallback implements MqttCallback
{
	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("MQTT : connectionLost\n" + cause);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) {
		dealDevMsg(topic, message);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token)	{ return; }

	private void dealDevMsg(String topic, MqttMessage message) {
		if (message.isRetained()) {
			return;
		}
		System.out.println("in dealDevMsg : topic:	" + topic + "\t");

		if(topic.startsWith("sports/sportInfo")) {
			dealSportInfo(message);
		}
		switch (topic) {
			case "Will":
				dealWill(message);
				break;

			default:
				break;
		}
	}

	//记录运动信息
	public void dealSportInfo(MqttMessage message) {

		JSONObject obj = JSON.parseObject(message.getPayload().toString());

		InfluxDealData.writeSportInfoIntoDB(obj.getString("tag"), obj.getDoubleValue("longitude"),
				obj.getDoubleValue("latitude"), obj.getDoubleValue("elevation"),
				obj.getDoubleValue("speed"), obj.getDoubleValue("direction_x"),
				obj.getDoubleValue("direction_y"), obj.getDoubleValue("direction_z"),
				obj.getDoubleValue("accelerated_x"), obj.getDoubleValue("accelerated_y"),
				obj.getDoubleValue("accelerated_z"), obj.getInteger("steps"));
	}


	//记录异常掉线
	public void dealWill(MqttMessage message) 	{

	}


}