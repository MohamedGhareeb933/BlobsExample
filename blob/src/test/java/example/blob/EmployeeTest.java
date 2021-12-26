package example.blob;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.blob.entity.Employee;
import example.blob.entity.EmployeeFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
public class EmployeeTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void Setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .defaultRequest(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                ).build();
    }

    @Test
    void Test1() throws Exception {
        this.mockMvc.perform(get("/employees")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void FindAll_Employee_Test() throws Exception {
        this.mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpectAll(
                        jsonPath("$._embedded.employeeList[*].id").isNotEmpty(),
                        jsonPath("$._embedded.employeeList[0].firstName").isString(),
                        jsonPath("$._embedded.employeeList[0].files[*].id").isNotEmpty(),
                        jsonPath("$._embedded.employeeList[0].files[0].name").isString()
                );
    }

    @Test
    void Find_Employees_Test() throws Exception {
        this.mockMvc.perform(get("/employees/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpectAll(
                        jsonPath("$.id").value(1),
                        jsonPath("$.firstName").value("mohamed"),
                        jsonPath("$.lastName").value("ghareeb"),
                        jsonPath("$.email").value("mo@gmail.com"),
                        jsonPath("$.files[0].id").value(2)
                );
    }

    @Test
    void Post_Employee_Test() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName("mohamed");
        employee.setLastName("ghareeb");
        employee.setFiles(new HashSet<EmployeeFile>());
        employee.setEmail("mohamend@gmail.com");

        String Payload = objectToJson(employee);

        this.mockMvc.perform(post("/employees").content(Payload))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void Put_Employee_Test()  throws Exception{

        Employee employee = new Employee();
        employee.setFirstName("mohamed");
        employee.setLastName("ghareeb");
        employee.setEmail("mohamedGhareeb@gmail.com");

        this.mockMvc.perform(put("/employees/{id}", 1).content(objectToJson(employee)))
                .andDo(print())
                .andExpect(status().isAccepted());
    }


     static String objectToJson(Object object)  {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }catch (JsonProcessingException exception) {
            throw new RuntimeException("can not map Object to String" + exception);
        }
    }
}
