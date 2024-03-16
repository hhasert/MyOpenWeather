Application in Jetpack Compose using the Open Weather API

After the Jetpack Compose course it was time to really learn and hence start developing an app (based on MarsPhotos initially to use the correct architecture). 
Since you can experiment with the OpenWeather API for free (limited traffic (60/minute) and signup is needed) at https://openweathermap.org/api, I decided to give it a spin to create an app. 
Currently it does not have a design, its just stretching my wings on the concepts and getting things working.

The project contains a secret.properties file containing the API Key from https://home.openweathermap.org/ which needs to be filled in locally. 

First steps are there, there is a navhost controller to support multiple screens and a viewmodel/repository etc according to the architecture in the courses. 

Struggling to get the locations permissions sorted, added screens (from the sample) and navhost launches that first, should of course be only when the permissions are not there // TODO //

For now it shows a location screen (prefilled in the view model with my location) and a next button to get the current weather. Next step will be to create some decent weather screen.
