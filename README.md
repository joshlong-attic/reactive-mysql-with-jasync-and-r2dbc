# Getting Started

You'll need to have a MySQL instance up and running in order to run this code. 
I got it working using Docker. Here's an example. 

``` 

docker run --name=orders-mysqldb -d -p 3306:3306 -e MYSQL_ROOT_HOST=% -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=orders -e MYSQL_PASSWORD=orders -e MYSQL_DATABASE=orders mysql/mysql-server:5.7.24


``` 

