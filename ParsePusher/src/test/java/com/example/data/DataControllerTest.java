package com.example.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DataControllerTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @InjectMocks
    private DataController dataController;

    @Mock
    private DataRepository dataRepository;

    private MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(dataController).build();
    }

    @Test
    public void getDataReturnsData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/data"))
                .andExpect(status().isOk());

        verify(dataRepository).findAll();
        verifyNoMoreInteractions(dataRepository);
    }

    @Test
    public void deleteDataFromRepository() throws Exception {
        mockMvc.perform(delete("/data"))
                .andExpect(status().isNoContent());

        verify(dataRepository).deleteAll();
        verifyNoMoreInteractions(dataRepository);
    }
}