package dev.bacha.CheapestTransferRoute.controller;


import dev.bacha.CheapestTransferRoute.model.CheapestRouteResponse;
import dev.bacha.CheapestTransferRoute.model.Transfer;
import dev.bacha.CheapestTransferRoute.model.TransferRequest;
import dev.bacha.CheapestTransferRoute.service.TransferService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TransferController.class)
public class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransferService transferService;

    private TransferRequest request;

    @BeforeEach
    public void setUp() {
        Transfer transfer1 = new Transfer(5, 10);
        Transfer transfer2 = new Transfer(10, 15);
        Transfer transfer3 = new Transfer(7, 12);

        List<Transfer> availableTransfers = Arrays.asList(transfer1, transfer2, transfer3);
        request = new TransferRequest(15, availableTransfers);
    }

    @Test
    public void testFindCheapestRoute() throws Exception {
        CheapestRouteResponse response = new CheapestRouteResponse(Arrays.asList(new Transfer(5, 10), new Transfer(10, 20)), 30, 15);
        when(transferService.findCheapestRoute(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"maxWeight\": 15, \"availableTransfers\": [ { \"weight\": 5, \"cost\": 10 }, { \"weight\": 10, \"cost\": 15 }, { \"weight\": 7, \"cost\": 12 } ] }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalCost").value(30))
                .andExpect(jsonPath("$.totalWeight").value(15))
                .andExpect(jsonPath("$.selectedTransfers.size()").value(2));
    }

    @Test
    public void testFindCheapestRouteWhenNoTransfers() throws Exception {
        TransferRequest emptyRequest = new TransferRequest(10, List.of());
        CheapestRouteResponse emptyResponse = new CheapestRouteResponse(List.of(), 0, 0);
        when(transferService.findCheapestRoute(emptyRequest)).thenReturn(emptyResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"maxWeight\": 10, \"availableTransfers\": [] }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalCost").value(0))
                .andExpect(jsonPath("$.totalWeight").value(0))
                .andExpect(jsonPath("$.selectedTransfers.size()").value(0));
    }
}