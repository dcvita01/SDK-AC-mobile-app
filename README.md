# SDK Access Control Android Mobile App

This project is a web application part of a system called SDK Access Control designed as a student project for CS graduate study programme subject Embedded Systems at FESB, University of Split. The system was designed in collaboration with [Stella](https://github.com/sandje00) and [Karlo](https://github.com/KarloMijaljevic).

## Prerequisites

* [Android Studio](https://developer.android.com/studio)

## Project setup

1. Follow the instructions [here](https://github.com/KarloMijaljevic/SDK-AC) to set up server side application
2. Import github project into Android studio
```
File -> New -> Project from Version Control -> GitHub
```
3. Enter [this](https://github.com/dcvita01/SDK-AC-mobile-app.git) repository URL and click clone
```
https://github.com/dcvita01/SDK-AC-mobile-app.git
```
5. Add one user to server database 
```
curl --request POST \
  --url http://localhost:5000/users \
  --header 'Content-Type: application/json' \
  --data '{
	"name": "Jane Doe",
	"email": "jane.doe@gmail.com",
	"mac": "00:00:51:12:53:02"
}'
```
6. Get user's ID
```
curl --request GET \
  --url http://localhost:5000/users
```
7. Edit file `app/src/main/res/values/strings.xml`

a) Run on a **real device**    
- find your (server's) local IP address
- open port 5000 in firewall
- set "server_ip" value to your IP address
- set "user_id" value to ID of user that you created earlier
        
```
<string name="server_ip">192.168.0.152</string>
<string name="user_id">620503ec5f2292af73e9b71b</string>
```
        
b) Run on an **emulator**    
- set "server_ip" value to 10.0.2.2
- change "user_id" value to ID of user that you created earlier
        
```
<string name="server_ip">10.0.2.2</string>
<string name="user_id">620503ec5f2292af73e9b71b</string>
```
4. Run app ( [real device](https://developer.android.com/training/basics/firstapp/running-app#RealDevice) or [emulator](https://developer.android.com/training/basics/firstapp/running-app#Emulator) ) 

    


