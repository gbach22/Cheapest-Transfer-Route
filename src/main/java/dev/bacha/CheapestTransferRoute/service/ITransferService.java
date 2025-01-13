package dev.bacha.CheapestTransferRoute.service;

import dev.bacha.CheapestTransferRoute.model.CheapestRouteResponse;
import dev.bacha.CheapestTransferRoute.model.TransferRequest;

public interface ITransferService {
    public CheapestRouteResponse findCheapestRoute(TransferRequest request);
}
