package com.example.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CallbackControllerTest {
    @Mock
    SubscriberProperties subscriberProperties;

    @Mock
    DataRepository dataRepository;

    @Mock
    RestOperations restOperations;

    @InjectMocks
    CallbackController callbackController;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(callbackController).build();
    }

    @Test
    public void gettingDataWithoutAHubSignatureHeaderStillSavesTheData() throws Exception {
        mockMvc.perform(post("/callback")
                .content("{}"))
                .andExpect(status().isOk());

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("X-Parse-Application-Id", "RlOF7ViCpvt0d7H8nE7TYHz8HbQtSCfma3GopAsg"); //TODO: Update ID
        headers.add("X-Parse-REST-API-Key", "kCxkFLu5TwgxCYrtwC2vB6Ru5NZOhR1NROJZCPxq"); //TODO: Update ID
        HttpEntity<?> requestEntity = new HttpEntity<>("{}", headers);
        HttpMethod method = HttpMethod.POST;
        verify(restOperations).exchange("https://api.parse.com/1/classes/sensorData", method, requestEntity, Map.class);
        verifyNoMoreInteractions(restOperations);
    }
}