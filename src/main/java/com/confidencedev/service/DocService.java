package com.confidencedev.service;

import com.confidencedev.model.Doc;
import com.confidencedev.utils.HttpUtils;
import io.github.dengliming.redismodule.redisjson.RedisJSON;
import io.github.dengliming.redismodule.redisjson.args.GetArgs;
import io.github.dengliming.redismodule.redisjson.args.SetArgs;
import io.github.dengliming.redismodule.redisjson.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class DocService {

    public static final String GET_DOCS_API = "https://jsonplaceholder.typicode.com/posts/5";
    public static final String REDIS_KEY = "posts";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    RedisJSON redisJSON;

    public String loadDocs() {
        log.info("Inside fetchDocs()");
        ResponseEntity<Doc> docEntity =
                restTemplate.exchange(GET_DOCS_API,
                        HttpMethod.GET,
                        HttpUtils.getHttpEntity(),
                        Doc.class);

        String result = redisJSON.set(REDIS_KEY, SetArgs
                .Builder.create(".", GsonUtils.toJson(docEntity.getBody())));
        return "Loaded: " + result;
    }

    public List<Doc>  fetchDocs() {
        log.info("Inside fetchDocsHistory()");
        Doc docData =
                redisJSON.get(REDIS_KEY,
                        Doc.class,
                        new GetArgs().path(".")
                                .indent("\t")
                                .newLine("\n")
                                .space(" "));
        log.info("allDocs: " + docData);
        return Collections.singletonList(docData);
    }
}
