package cn.kafka;

import  static cn.WebSocket.WebSocket.wbSockets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import cn.WebSocket.WebSocket;
 
public class ConsumerKafka extends Thread {

    private KafkaConsumer<String,String> consumer;
    private String topic = "websocket";
   // WebSocket wbsocekt=new WebSocket();
    public ConsumerKafka(){

    }

    @Override
    public void run(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.3.252:9092");
        props.put("group.id", "ytna");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "15000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<String,String>(props);
        consumer.subscribe(Arrays.asList(this.topic));
        while (true){
        	 
        	 try {
	               //消费数据，并设置超时时间
	                ConsumerRecords<String, String> records = consumer.poll(10000);
	                //Consumer message
	                for (ConsumerRecord<String, String> record : records) {
	                	System.out.println(record.value());
	                    //Send message to every client
	                    for (WebSocket webSocket :wbSockets){
	                    	System.out.println(record.value());
	                        webSocket.sendMessage(record.value());
	                    }
	                }
	            }catch (IOException e){
	                System.out.println(e.getMessage());
	                continue;
	            }
	         
        }
    }

    public void close() {
        try {
            consumer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

/*  public static void main(String[] args){
        ConsumerKafka consumerKafka = new ConsumerKafka();
        consumerKafka.start();
    }*/
}