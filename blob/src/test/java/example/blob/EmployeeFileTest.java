package example.blob;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeFileTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    MockMultipartFile file;

    @BeforeEach
    void Setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        file = new MockMultipartFile("file", "file.txt", "text/plain", "helloTest".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void Upload_EmployeeFile_test() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/files").file(file)
                        .param("description", "empty description")
                        .param("id", "1")
                        .param("type", "RESUME"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void Update_EmployeeFile_test() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/files/{id}", 1)
                        .file(file)
                        .with(new RequestPostProcessor() {
                            @Override
                            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                                request.setMethod("PUT");
                                return request;
                            }
                        })
                        .param("description", "empty description")
                        .param("type", "RESUME"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }


}
