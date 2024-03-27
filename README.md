# **BDTechCenter back-end repo**

## How to run? 🐳
To run the backend you need WSL2 with Docker and CNTLM installed

Follow the next steps: 

First verify all services **application.yaml**, the property **spring.profiles.active** needs to be **docker**

Allow file execution

```powershell
sudo chmod +x ./startDocker.sh
```


Fix possible interpreter error
```powershell
sed -i 's/\r$//' ./startDocker.sh
```


Run the script to build all services
```powershell
./startDocker.sh
```


Build the containers
```powershell
docker compose build
```


Up all containers
```powershell
docker compose up
```


On wls run the following command to get docker eth0
```powershell
ip addr show eth0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}'
```


On powershell as admin run the following command to allow the application accessible on ip address
```powershell
netsh interface portproxy add v4tov4 listenport=8765 listenaddress=0.0.0.0 connectport=8765 connectaddress=<dockerEth0>
```

```powershell
netsh interface portproxy add v4tov4 listenport=8766 listenaddress=0.0.0.0 connectport=8766 connectaddress=<dockerEth0>
```

Now the applications is running you can access by using the url http:{ip}:8765 and needs to appear the eureka interface
