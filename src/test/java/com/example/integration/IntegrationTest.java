package com.example.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.TransactionServiceApplication;
import com.example.entity.Persistence;
import com.example.entity.Request;
import com.example.entity.Response;
import com.example.entity.TransactionAmount;
import com.example.service.AuthorizationLoadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TransactionServiceApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationLoadService authorizationLoadService;

    @Test
    public void testDoAuthorizationEndpoint() throws Exception {
        Request request = new Request();
        request.setUserId("user123");
        request.setMessageId("message123");
        request.setTransactionAmount(new TransactionAmount("100.00", "USD", "debit"));

        Response response = new Response();
        response.setMessageId("message123");
        response.setUserId("user123");
        response.setBalance(new TransactionAmount("50.00", "USD", "debit"));

        when(authorizationLoadService.doAuthorization(any(Request.class), any(Persistence.class))).thenReturn(response);

        this.mockMvc.perform(put("/authorization/{messageId}", "message123")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.messageId", equalTo("message123")))
                .andExpect(jsonPath("$.userId", equalTo("user123")))
                .andExpect(jsonPath("$.balance.amount", equalTo("50.00")))
                .andExpect(jsonPath("$.balance.currency", equalTo("USD")))
                .andExpect(jsonPath("$.balance.debitOrCredit", equalTo("debit")));
    }

    @Test
    public void testDoLoadEndpoint() throws Exception {
        Request request = new Request();
        request.setUserId("user123");
        request.setMessageId("message123");
        request.setTransactionAmount(new TransactionAmount("100.00", "USD", "credit"));

        Response response = new Response();
        response.setMessageId("message123");
        response.setUserId("user123");
        response.setBalance(new TransactionAmount("200.00", "USD", "credit"));

        when(authorizationLoadService.doLoad(any(Request.class), any(Persistence.class))).thenReturn(response);

        mockMvc.perform(put("/load/{messageId}", "message123")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.messageId", equalTo("message123")))
                .andExpect(jsonPath("$.userId", equalTo("user123")))
                .andExpect(jsonPath("$.balance.amount", equalTo("200.00")))
                .andExpect(jsonPath("$.balance.currency", equalTo("USD")))
                .andExpect(jsonPath("$.balance.debitOrCredit", equalTo("credit")));
    }
}
