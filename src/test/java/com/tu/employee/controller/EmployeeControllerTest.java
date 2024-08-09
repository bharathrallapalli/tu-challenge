package com.tu.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tu.employee.data.entity.Employee;
import com.tu.employee.model.CreateRequest;
import com.tu.employee.service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    HttpHeaders headers;

    @BeforeEach
    public void setup() {

        headers = new HttpHeaders();
        headers.put("Request-Id", List.of(UUID.randomUUID().toString()));
    }

    @Test
    void testEmptyValues() throws Exception {
        var createRequest = CreateRequest.builder().name("").build();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)).headers(headers))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("sin: sin can't be null or blank")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("name: name can't be null or blank")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("phoneNumber: phoneNumber can't be null or blank")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("address: address can't be null or blank")))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("title: title can't be null" +
                        " or blank")));
    }

    @Test
    void testInvalidPhoneNumber() throws Exception {
        var createRequest = CreateRequest.builder()
                .name("Bharath")
                .address("1502- 15 Michael Power Pl, Etobicoke, ON, M9A5G4")
                .phoneNumber("6472223282s")
                .dateOfBirth(LocalDate.of(1991, 03, 11))
                .title("Lead Software Engineer")
                .sin("123456789")

                .build();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)).headers(headers))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("phoneNumber: phoneNumber can only contain 10 digits")));

    }

    @Test
    void testInvalidSIN() throws Exception {
        var createRequest = CreateRequest.builder()
                .name("Bharath")
                .address("1502- 15 Michael Power Pl, Etobicoke, ON, M9A5G4")
                .phoneNumber("6472223282")
                .dateOfBirth(LocalDate.of(1991, 03, 11))
                .title("Lead Software Engineer")
                .sin("123456")

                .build();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/employee").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)).headers(headers))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("sin: SIN can only contain 9 digits")));

    }

    @Test
    void testValidData() throws Exception {
        var employee = Employee.builder()
                .employeeId(1L)
                .name("Bharath")
                .address("1502- 15 Michael Power Pl, Etobicoke, ON, M9A5G4")
                .phoneNumber("6472223282")
                .dateOfBirth(LocalDate.of(1991, 03, 11))
                .title("Lead Software Engineer")
                .sin("6789")
                .build();


        var createRequest = CreateRequest.builder()
                .name("Bharath")
                .address("1502- 15 Michael Power Pl, Etobicoke, ON, M9A5G4")
                .phoneNumber("6472223282")
                .dateOfBirth(LocalDate.of(1991, 03, 11))
                .title("Lead Software Engineer")
                .sin("123456789")
                .build();

        given(employeeService.createEmployee(any(CreateRequest.class))).willReturn(employee);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest))
                .headers(headers));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.is("Bharath")))
                .andExpect(jsonPath("$.address", Matchers.is("1502- 15 Michael Power Pl, Etobicoke, ON, M9A5G4")))
                .andExpect(jsonPath("$.phoneNumber", Matchers.is("6472223282")))
                .andExpect(jsonPath("$.dateOfBirth", Matchers.is("1991-03-11")))
                .andExpect(jsonPath("$.title", Matchers.is("Lead Software Engineer")))
                .andExpect(jsonPath("$.sin", Matchers.is("6789")))
                .andExpect(jsonPath("$.employeeId", Matchers.is(1)));
    }
}
