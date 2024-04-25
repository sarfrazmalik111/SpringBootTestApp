//package com.test.aKafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/kafka")
//public class UserResourceController {
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//    @Autowired
//    private KafkaTemplate<String, KafkaUser> kafkaJsonTemplate;
//
//    private static final String TOPIC = "Kafka_Example";
//
//    @GetMapping("/publish/{name}")
//    public String publish(@PathVariable("name") final String name) {
//        kafkaTemplate.send(TOPIC, name);
//        return "Published successfully";
//    }
//    
//    @GetMapping("/publish-json/{name}")
//    public String publishJSON(@PathVariable("name") final String name) {
//    	kafkaJsonTemplate.send(TOPIC, new KafkaUser(name, "Technology", 12000L));
//        return "Published successfully";
//    }
//}