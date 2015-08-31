package net.ars.sample.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	private static final int PLAY_TIME_SEC = 60;
	
	public static void main(String[] args) throws Exception {
		// We are only interested in the InvalidActorNameException
		//	System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "error");
		// see: simplelogger.properties
		
		ActorSystem actorSystem = ActorSystem.create("ars-akka");
		ActorRef masterRef = actorSystem.actorOf(Props.create(Executive.class), "executive");
		masterRef.tell(Message.BEGIN, ActorRef.noSender());
		
		Logger logger = LoggerFactory.getLogger(Main.class);
		// force the printing of this information - hence error
		logger.error("The Main thread going off to sleep for " + PLAY_TIME_SEC + " seconds...");
		
		Thread.sleep(PLAY_TIME_SEC * 1000);
		actorSystem.shutdown();
	}
}
