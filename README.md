# Partner_Campaigns

Partner_Campaigns is implemented using Spring Boot and Java 8. Restful services are created to manage ad campaigns. 
Partners can call services to create, update and find campaigns.

### How to build

mvn clean install

### How to run

java -jar target/partner_campaigns-01.00.00.00.jar

### Create Campaign

Campaign can be created by calling service URL http://localhost:8080/campaign/create
Request for the service is json object.

HTTP method : POST
Headers:

Content-Type : application/json
Accept : application/json

{
	"partner_id" : "1",
	"duration" : "60",
	"ad_content" : "test content",
	"ad_title" : "promo title",
	"ad_status" : "ACTIVE"
}

### Find Campaigns for content, title and duration.

Campigns can be found using ad content, title and duration. Response contains active and inactive campigns that match the criteria.

Http method : GET

 http://localhost:8080/campaign/search?title=test


 http://localhost:8080/campaign/search?title=test&duration=5


 http://localhost:8080/campaign/search?content=test

### Find Campaign by partner.

Campigns can be found using partner id. Response contains only active campaigns

Http method : GET

 http://localhost:8080/campaign/partnersearch?partnerId=1

 http://localhost:8080/campaign/search?title=test

### Update Campaign

Campaign can be updated by calling service URL http://localhost:8080/campaign/update
Request for the service is json object. Returns updated campaign object.

HTTP method : POST
http://localhost:8080/campaign/update

Headers:

Content-Type : application/json
Accept : application/json

{
	"partner_id" : "1",
	"duration" : "60",
	"ad_content" : "test content",
	"ad_title" : "promo title",
	"ad_status" : "ACTIVE"
}


