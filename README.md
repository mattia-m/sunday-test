# sunday-test

This rest application allows the user to manage patient visits, 
adding basic info to the visit.

## Running

mvn package creates a war that you will be able to add to 
any wildfly 26 deployments

the whole APi documentation can then be seen at 
{wildflyhost}:{wildflyPort}/sunday-test/api/openapi.json

## Storing data
At the moment every data is actually stored on H2 in memory db, 
so you will lose everything at every reboot.