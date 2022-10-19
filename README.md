# restuserclient

DESCRIPTION

Show User List is a (very) basic example of using feature flags to enable an application feature
The application is calling a REST service (https://reqres.in/api/users) which provides a list of users. 
If the feature flag is enabled for the user the program will return the number of users in the first page and the list, with first and last name. 
If the feature flag is not enabled, the user will just be returned the count of users on the page. 

REQUIREMENTS

In order to run the application locally, you need to have installed:
 - Java >= 1.8
 - Maven
 
CREATE THE FEATURE FLAG

 - Connect to your LauchDarkly environment and create the feature flag show-user-list for testing (remember to change the name in UserClient.java if you change the flag key)
 - Setup a rule for your flag, such as if the email ends with "gmail.com" the flag is served as false. For instructions on how to do so, please check: https://docs.launchdarkly.com/home/flags/targeting-rules
 - Update the SDK_KEY in UserClient.java according to your SDK Key, which can be found by going to https://app.launchdarkly.com/settings/projects and selecting you environment
 

HOW TO DEPLOY

 - Clone this repository locally: git clone 
 - Compile and package the code: mvn clean package
 - Run the code: java -jar target/restuserclient-1.0-SNAPSHOT.jar
 You can play around with different rules for the feature flag 