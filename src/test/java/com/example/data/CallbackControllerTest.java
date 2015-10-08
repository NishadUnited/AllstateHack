package com.example.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CallbackControllerTest {
    @Mock
    SubscriberProperties subscriberProperties;

    @Mock
    DataRepository dataRepository;

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

        verify(dataRepository).insert(any(DataEntity.class));
    }
}