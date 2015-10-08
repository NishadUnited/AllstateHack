package api;

import com.example.Application;
import com.example.data.DataEntity;
import com.example.data.DataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class DeleteData {
    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUpWebapp() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void insertData() {
        DataEntity data = new DataEntity();
        DataEntity data2 = new DataEntity();
        dataRepository.insert(Arrays.asList(data, data2));
    }

    @Test
    public void deleteData() throws Exception {
        mockMvc.perform(delete("/data")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(dataRepository.count(), is(0L));
    }
}