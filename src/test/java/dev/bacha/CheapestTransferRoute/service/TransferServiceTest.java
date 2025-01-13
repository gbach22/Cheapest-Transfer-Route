package dev.bacha.CheapestTransferRoute.service;

import dev.bacha.CheapestTransferRoute.model.CheapestRouteResponse;
import dev.bacha.CheapestTransferRoute.model.Transfer;
import dev.bacha.CheapestTransferRoute.model.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {

    private ITransferService transferService;

    @BeforeEach
    void setUp() {
        transferService = new TransferService();
    }

    @Test
    void testFindCheapestRouteWithValidInput() {
        List<Transfer> availableTransfers = List.of(
                new Transfer(5, 10),
                new Transfer(10, 20),
                new Transfer(3, 5),
                new Transfer(8, 15)
        );

        List<Transfer> selectedTransfers = List.of(
                new Transfer(5,10),
                new Transfer(10, 20)
        );

        TransferRequest request = new TransferRequest(15, availableTransfers);

        CheapestRouteResponse response = transferService.findCheapestRoute(request);

        assertEquals(30, response.totalCost());
        assertEquals(15, response.totalWeight());
        assertEquals(2, response.selectedTransfers().size());
        assertTrue(compareLists(selectedTransfers, response.selectedTransfers()));
    }

    @Test
    void testFindCheapestRouteWithNoTransfers() {
        TransferRequest request = new TransferRequest(15, List.of());

        CheapestRouteResponse response = transferService.findCheapestRoute(request);

        assertEquals(0, response.totalCost());
        assertEquals(0, response.totalWeight());
        assertEquals(0, response.selectedTransfers().size());
    }

    @Test
    void testFindCheapestRouteWithZeroMaxWeight() {
        List<Transfer> transfers = List.of(
                new Transfer(5, 10),
                new Transfer(10, 20)
        );
        TransferRequest request = new TransferRequest(0, transfers);

        CheapestRouteResponse response = transferService.findCheapestRoute(request);

        assertEquals(0, response.totalCost());
        assertEquals(0, response.totalWeight());
        assertEquals(0, response.selectedTransfers().size());
    }

    @Test
    void testFindCheapestRouteWithSingleTransferFittingWeight() {
        List<Transfer> transfers = List.of(
                new Transfer(5, 10)
        );
        TransferRequest request = new TransferRequest(5, transfers);

        CheapestRouteResponse response = transferService.findCheapestRoute(request);

        assertEquals(10, response.totalCost());
        assertEquals(5, response.totalWeight());
        assertEquals(1, response.selectedTransfers().size());
        assertTrue(compareLists(transfers, response.selectedTransfers()));
    }

    @Test
    void testFindCheapestRouteWithTransfersExceedingWeight() {
        List<Transfer> transfers = List.of(
                new Transfer(10, 50),
                new Transfer(15, 70),
                new Transfer(20, 90)
        );
        TransferRequest request = new TransferRequest(5, transfers);

        CheapestRouteResponse response = transferService.findCheapestRoute(request);

        assertEquals(0, response.totalCost());
        assertEquals(0, response.totalWeight());
        assertEquals(0, response.selectedTransfers().size());
    }

    private boolean compareLists(List<Transfer> resultTransfers, List<Transfer> selectedTransfers) {
        boolean result = true;
        for (int i = 0; i < resultTransfers.size(); i++) {
            if((resultTransfers.get(i).weight() != selectedTransfers.get(i).weight())
                    || (resultTransfers.get(i).cost() != selectedTransfers.get(i).cost())) {

                System.out.println(resultTransfers.get(i).weight());
                System.out.println(selectedTransfers.get(i).weight());
                result = false;
                break;
            }
        }

        return result;
    }
}