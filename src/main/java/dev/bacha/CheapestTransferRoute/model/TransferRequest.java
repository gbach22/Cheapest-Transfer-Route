package dev.bacha.CheapestTransferRoute.model;

import java.util.List;

public record TransferRequest(
        int maxWeight,
        List<Transfer> availableTransfers
) {}
