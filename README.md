# akka-actor-naming  
# *The effect of child instantiation being async and the child actor names being reused*  
  
Two simple solutions  
  
- child instantiation needs to be synchronous with postRestart  
  
- check for existence of children/child count on postRestart before instantiating asynchronously  
  
  
  
# Compile and run  
  
`mvn clean compile`  

`mvn exec:java -Dexec.mainClass="net.ars.sample.actor.Main"`  
