package api;

import com.example.Application;
import com.example.data.SubscriberProperties;
import com.example.data.DataEntity;
import com.example.data.DataRepository;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class Callbacks {
    @Autowired
    private SubscriberProperties subscriberProperties;

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
    public void purgeData() {
        dataRepository.deleteAll();
    }

    @Test
    public void callbackSavesPostDataAndHubSignature() throws Exception {
        String sentData = "{ \"some\" : \"data\" }";

        long count = dataRepository.count();

        mockMvc.perform(post("/callback")
                .header("X-Hub-Signature", "sha1=11697c887c39e0d2435d8566dc689bb84fcca82d")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sentData))
                .andExpect(status().isOk());

        assertThat(dataRepository.count(), is(count + 1));

        List<DataEntity> allData = dataRepository.findAll();
        DataEntity dataEntity = allData.get(allData.size() - 1);
        assertThat(dataEntity.getData(), is(((DBObject) JSON.parse(sentData))));
        assertThat(dataEntity.getSignature(), is("sha1=11697c887c39e0d2435d8566dc689bb84fcca82d"));
    }
}