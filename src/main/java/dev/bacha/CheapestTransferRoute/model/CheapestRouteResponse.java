package dev.bacha.CheapestTransferRoute.model;

import java.util.List;

public record CheapestRouteResponse(
        List<Transfer> selectedTransfers,
        int totalCost,
        int totalWeight
) {}
