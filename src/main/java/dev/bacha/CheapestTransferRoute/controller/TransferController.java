package dev.bacha.CheapestTransferRoute.controller;

import dev.bacha.CheapestTransferRoute.model.CheapestRouteResponse;
import dev.bacha.CheapestTransferRoute.model.TransferRequest;
import dev.bacha.CheapestTransferRoute.service.ITransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final ITransferService transferService;

    public TransferController(ITransferService transferService) {
        this.transferService = transferService;
    }


    @PostMapping
    public ResponseEntity<CheapestRouteResponse> findCheapestRoute(@RequestBody TransferRequest request) {
        CheapestRouteResponse response = transferService.findCheapestRoute(request);
        return ResponseEntity.ok(response);
    }

}