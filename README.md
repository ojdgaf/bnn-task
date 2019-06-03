# bnn-task

The goal is to generate a basic dashboard using Power BI

## How to use this application

1. Create an Azure Active Directory tenant, create some users in your tenant
2. Sign up for Power BI with your user account that you created
3. Upload data in Power BI Desktop application and create report
4. Add new role for row-level security
5. Publish the report to the Power BI Service
6. Fill application.properties file with your credentials

## How it works
Angular application obtains ID token after providing a user's credentials to Microsoft identity platform (OAuth authentication) and accesses Spring Boot API (this application).
Each request to the API must include HTTP header "Authorization: Bearer id_token".
This way the API can validate user's authorities via Microsoft public keys. 
After successful authentication, Spring Boot application interacts with Power BI REST API on behalf of Power BI Pro account and returns embed tokens for reports which can be consumed by frontend libraries. 
Displayed information is restricted to current user only.

## Issues

1. There is no way to display embed tiles (at least we could not do this). 
Either we don't have a necessary information for the HTML request (page name, visual name) or a tile chart simply loads infinitely.
Instead we are working with reports and each reports contains only one chart. Endpoint for fetching embed tokens for tiles still exists tho.

2. Integration with Power BI REST API is achieved by admin account credentials. Consider to check out "azure-code-auth" branch if all users inside your tenand have Power BI Pro licenses

3. Microsoft public keys are not cached so the application should make one additional request to the Microsoft each time user is trying to authenticate

4. Although Power BI API is unreachable sometimes, the API provides poor functionality for handling this kind of situation (I was thinking about circuit breaker)

5. Unfortunately Microsoft does not provide endpoints for creating embed tokens for a bunch of elements therefore applications have to use loops

6. You may face some [troubles](https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception-in-j) running this application from command line if you have multiple JDK versions installed on your machine

7. Integration tests are missing
