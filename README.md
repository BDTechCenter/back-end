# **BDTechCenter back-end repo**

## How to run? 🐳
To run the backend you need WSL2 with Docker and CNTLM installed

Follow the next steps: 

first verify all services **application.yaml**, the property **spring.profiles.active** needs to be **docker**

allow file execution

```ps
sudo chmod +x ./startDocker.sh
```


fix possible interpretation errors
```ps
sed -i 's/\r$//' ./startDocker.sh
```


run the script to build all services
```ps
./startDocker.sh
```


build the containers
```ps
docker compose build
```


up all containers
```ps
docker compose up
```


on wls run the following command to get docker eth0
```ps
ip addr show eth0 | grep -oP '(?<=inet\s)\d+(\.\d+){3}'
```


on powershell as admin run the following command to allow the application accessible on ip address
```ps
netsh interface portproxy add v4tov4 listenport=8765 listenaddress=0.0.0.0 connectport=8765 connectaddress=<dockerEth0>
```

```ps
netsh interface portproxy add v4tov4 listenport=8766 listenaddress=0.0.0.0 connectport=8766 connectaddress=<dockerEth0>
```


now the applications is running you can access by using the url http:{ip}:8765 and needs to appear the eureka interface