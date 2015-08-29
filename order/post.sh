#!/bin/bash

curl -i -H "Content-Type: application/json" -X POST -d '{ "orderId":"1","customerId":"craig", "productId": "yellowwidget", "productQty", 2 }' http://localhost:8080/orders
