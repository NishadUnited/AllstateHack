package com.example.data;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Callback", description = "Callback API")
public class CallbackController {
    private static final Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private SubscriberProperties subscriberProperties;

    @Autowired
    private DataRepository dataRepository;

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

        DataEntity data = new DataEntity();
        data.setSignature(signature);
        data.setData(((DBObject) JSON.parse(body)));
        dataRepository.insert(data);
    }
}