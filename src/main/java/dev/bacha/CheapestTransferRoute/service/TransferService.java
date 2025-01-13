package dev.bacha.CheapestTransferRoute.service;

import dev.bacha.CheapestTransferRoute.model.CheapestRouteResponse;
import dev.bacha.CheapestTransferRoute.model.Transfer;
import dev.bacha.CheapestTransferRoute.model.TransferRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TransferService implements ITransferService {

    @Override
    public CheapestRouteResponse findCheapestRoute(TransferRequest request) {
        int maxWeight = request.maxWeight();
        List<Transfer> availableTransfers = new ArrayList<>(request.availableTransfers());

        availableTransfers.sort(new Comparator<Transfer>() {
            @Override
            public int compare(Transfer t1, Transfer t2) {
                return Integer.compare(t1.weight(), t2.weight());
            }
        });

        int [][] dp = getDpTable(availableTransfers, maxWeight);
        return reconstructResponse(dp, maxWeight, availableTransfers);

    }

    private CheapestRouteResponse reconstructResponse(int[][] dp, int maxWeight, List<Transfer> availableTransfers) {
        List<Transfer> selectedTransfers = new ArrayList<>();
        int totalCost = dp[dp.length - 1][dp[0].length - 1];
        int totalWeight = 0;
        int n = availableTransfers.size();

        for(int i = n, w = maxWeight; i > 0 && totalCost > 0; i--) {
            if(dp[i][w] != dp[i - 1][w]) {
                Transfer t = availableTransfers.get(i - 1);
                selectedTransfers.add(t);
                totalWeight += t.weight();
                totalCost -= t.cost();
                w -= t.weight();
            }
        }

        return new CheapestRouteResponse(selectedTransfers.reversed(), dp[dp.length - 1][dp[0].length - 1], totalWeight);
    }

    private int[][] getDpTable(List<Transfer> availableTransfers, int maxWeight) {
        int [][] dp = new int[availableTransfers.size() + 1][maxWeight+1];

        for(int i = 0; i < availableTransfers.size() + 1; i++) {dp[i][0] = 0;}
        for(int w = 0; w < maxWeight + 1; w++) {dp[0][w] = 0;}

        for(int i = 1; i < availableTransfers.size() + 1; i++) {
            Transfer transfer = availableTransfers.get(i - 1);

            for(int w = 0; w < maxWeight + 1; w++) {
                if(transfer.weight() > w) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - transfer.weight()] + transfer.cost());
                }
            }
        }
        return dp;
    }

}
