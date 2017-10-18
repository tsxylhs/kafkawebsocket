package cn.Thread;

import cn.kafka.ConsumerKafka;


public class RunThread {
    public RunThread(){
        ConsumerKafka kafka = new ConsumerKafka();
        kafka.start();
    }
}