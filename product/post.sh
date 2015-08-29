#!/bin/bash

curl -i -H "Content-Type: application/json" -X POST -d '{ "productId":"yellowwidget","productName":"Yellow Widget"}' http://localhost:8080/products
