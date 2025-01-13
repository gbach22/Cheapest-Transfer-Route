# Cheapest-Transfer-Route

This project implements a service that helps find the cheapest transfer route based on a list of 
available transfers and their associated weights and costs. The service uses dynamic programming to solve
the problem similar to the Knapsack problem, where the goal is to select a set of transfers that do not
exceed a maximum weight and maximize the total cost.

Project Structure
1. Controller Layer (TransferController)
The TransferController class exposes a RESTful API for clients to interact with the service. It listens
for POST requests to the /api/transfers endpoint, receives a TransferRequest, and returns a CheapestRouteResponse.

2. Service Layer (TransferService)
The TransferService contains the business logic for calculating the cheapest route using dynamic 
programming. It:
    Sorts available transfers by weight.
    Constructs a dynamic programming table to compute the cheapest route.
    Reconstructs the selected transfers and returns the total cost and weight.

3. Model Layer (Transfer, TransferRequest, CheapestRouteResponse)
   Transfer: Represents an individual transfer with a weight and cost.
   TransferRequest: Represents the request body for calculating the cheapest route, including the maximum weight and a 
   list of available transfers.
   CheapestRouteResponse: Represents the response body that includes the selected transfers, the total cost, and the 
   total weight.

Running the Application
After successfully running the application, the API will be available at http://localhost:8080/api/transfers.

I used PostMan for testing Post request. there is also transfers.http, which does the same thing