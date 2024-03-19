# **BDTechCenter back-end repo**

## How to run? 🐳
To run the backend you need WSL2 with Docker and CNTLM installed

Follow the next steps: 

First verify all services **application.yaml**, the property **spring.profiles.active** needs to be **docker**

Allow file execution

```ps
sudo chmod +x ./startDocker.sh
```


Fix possible interpretation errors
```ps
sed -i 's/\r$//' ./startDocker.sh
```


Run the script to build all services
```ps
./startDocker.sh
```


Build the containers
```ps
docker compose build
```


Up all containers
```ps
docker compose up
```


On wls run the following command to get docker eth0
```ps
ip addr show eth0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}'
```


On powershell as admin run the following command to allow the application accessible on ip address
```ps
netsh interface portproxy add v4tov4 listenport=8765 listenaddress=0.0.0.0 connectport=8765 connectaddress=<dockerEth0>
```

```ps
netsh interface portproxy add v4tov4 listenport=8766 listenaddress=0.0.0.0 connectport=8766 connectaddress=<dockerEth0>
```

Now the applications is running you can access by using the url http:{ip}:8765 and needs to appear the eureka interface
