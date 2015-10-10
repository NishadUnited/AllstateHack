package com.example.data;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "Callback", description = "Callback API")
public class CallbackController {
    private static final Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private RestOperations restOperations;

    private String facebookBaseUrl = "https://api.parse.com/1/classes/sensorData";

    @ApiOperation(
            value = "Receive A6 data",
            nickname = "callback_id"
    )
    @RequestMapping(
            value = "/callback",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void callback(@ApiParam(name = "a6Request", value = "The A6 request", required = true)
                         @RequestBody String body,
                         @ApiParam(name = "X-Hub-Signature", value = "The A6 X-Hub-Signature", required = true)
                         @RequestHeader(value = "X-Hub-Signature", required = false)
                         String signature) {
        logger.debug("/callback: body = [" + body + "]");

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("X-Parse-Application-Id", "RlOF7ViCpvt0d7H8nE7TYHz8HbQtSCfma3GopAsg"); //TODO: Update ID
        headers.add("X-Parse-REST-API-Key", "kCxkFLu5TwgxCYrtwC2vB6Ru5NZOhR1NROJZCPxq"); //TODO: Update ID
        restOperations.exchange(facebookBaseUrl, HttpMethod.POST, new HttpEntity<>(body, headers), Map.class);
//        restOperations.postForObject(facebookBaseUrl, body, null);
    }
}