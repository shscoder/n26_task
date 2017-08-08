

# I have used java 1.8 and maven 3.2.2 to compile and build the solution

# For the in memory store i used ConcurrentNavigableMap where epoch time is the key and transaction list is the value. As ConcurrentNavigableMap store in orders so i used tailMap function to get the transactions those are inserted on the last 60 seconds.Also, i used DoubleSummaryStatistics which is available on java 1.8 to calculate sum,avg,max,min and count.


#Some test case are written to test the application functionality.


# To run the application run the below command

mvn clean
mvn install

Then goto target folder and run the below command

java -jar n26-0.0.1.jar

It will start the embaded tomcat on 127.0.0.1:8080

# Test the rest api

POST http://127.0.0.1:8080/transactions
POST body is: 
{
"amount":100.5,
"timestamp":9876543211111
}


GET http://127.0.0.1:8080/statistics


