package com.confidencedev.controller;

import com.confidencedev.config.MQConfig;
import com.confidencedev.model.Doc;
import com.confidencedev.model.Message;
import com.confidencedev.service.DocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000") // From client
@RestController
@RequestMapping("/doc")
@Slf4j
public class DocController {

    @Autowired
    private DocService docService;

    @Autowired
    private RabbitTemplate template;

    @PostMapping
    private String loadRedisData(@RequestBody Message message){
        template.convertAndSend(MQConfig.MESSAGE_EXCHANGE,
                MQConfig.MESSAGE_ROUTING,
                message);
        return docService.loadDocs();
    }

    @GetMapping
    private List<Doc> fetchRedisData(){
        return docService.fetchDocs();
    }

}
