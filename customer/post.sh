#!/bin/bash

curl -i -H "Content-Type: application/json" -X POST -d '{ "customerId":"darren","customerName":"Darren Jackson"}' http://localhost:8080/customers
