package api;

import com.example.Application;
import com.example.data.DataEntity;
import com.example.data.DataRepository;
import com.mongodb.BasicDBObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class GetData {
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
    public void getData() throws Exception {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("insights", Collections.singletonList("vehicle.health.arrived"));
        basicDBObject.put("data", Collections.singletonMap("vehicleHealthUpload", "vehicle health"));
        basicDBObject.put("groupId", "group-id");
        basicDBObject.put("sensorId", "sensor-id");

        DataEntity data = new DataEntity();
        data.setData(basicDBObject);
        data.setSignature("sha1=11697c887c39e0d2435d8566dc689bb84fcca82d");
        dataRepository.insert(data);

        ClassPathResource classPathResource = new ClassPathResource("responses/getData.json");
        String responseBody = new String(Files.readAllBytes(Paths.get(classPathResource.getURI())));

        mockMvc.perform(get("/data")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }
}