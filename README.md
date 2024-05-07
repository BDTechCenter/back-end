# **BDTechCenter back-end repo**

## How to run? 🐳
To run the backend you need WSL2 with Docker and CNTLM installed


### application.yaml files
First verify all services **application.yaml**, the property **spring.profiles.active** needs to be **docker**

application.yaml e.g.
````yaml
spring:
  profiles:
    active: docker
````

### application shell script
Allow file execution

```powershell
sudo chmod +x ./.devops/start-docker.sh
```

Fix possible interpreter error
```powershell
sed -i 's/\r$//' ./.devops/start-docker.sh
```

Run the script initialize the application
```powershell
./.devops/start-docker.sh
```

### port binding

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

Now the applications is running! Access ``http://{IP}:8765`` to appear the eureka interface.
