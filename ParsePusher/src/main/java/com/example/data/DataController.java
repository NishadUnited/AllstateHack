package com.example.data;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
@Api(value = "Data", description = "Data API")
public class DataController {
    private final DataRepository dataRepository;

    @Autowired
    public DataController(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @ApiOperation(
            value = "Get all data",
            nickname = "get_data_id"
    )
    @RequestMapping(
            value = "/data",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Map> getData() {
        return StreamSupport.stream(dataRepository.findAll().spliterator(), false)
                .map(dataEntity -> {
                    Map<Object, Object> map = new HashMap<>();

                    String signature = dataEntity.getSignature();
                    map.put("signature", signature);

                    @SuppressWarnings("unchecked")
                    Map<String, Object> data = dataEntity.getData().toMap();
                    map.put("data", data);
                    return map;
                })
                .collect(toList());
    }

    @ApiOperation(
            value = "Delete all data",
            nickname = "delete_data_id"
    )
    @RequestMapping(
            value = "/data",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteData() {
        dataRepository.deleteAll();
    }
}