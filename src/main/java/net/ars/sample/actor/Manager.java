package net.ars.sample.actor;

import akka.actor.ActorRef;
import akka.actor.InvalidActorNameException;
import akka.actor.Props;

public class Manager extends ArsUntypedSupervisorActor {
	
	public static final int NUM_WORKER = 10;
	
	public Manager() {
		this.maxNumChildren = NUM_WORKER;
	}

	@Override
    public void postRestart(final Throwable reason) throws Exception {
		if(reason instanceof InvalidActorNameException) {
			logger.error("Holy Kaw - the one that we were looking out for...", reason);
			logger.error("Don't panic, go back to Manager class and perform one of the following actions...");
			logger.error("1. change the postRestart to make a synchronous call to start instead of sending a message");
			logger.error("2. uncomment the check for the children in the start method");
		}
    	super.postRestart(reason);
    	getSelf().tell(Message.RESTART, getSelf());
    	// start();
    }
	
	@Override
	public void onReceive(final Object msg) throws Exception {
		if(msg instanceof Message) {
			Message message = (Message)msg;
			switch(message) {
				case BEGIN:
				// there it goes
				case RESTART:
					start();
					break;
				case TRACK:
					bomb();
					break;
				default:
					super.handleMessage(message);
			}
		} else {
			super.handleMessage(msg);
		}
	}
		
	private void start() {
		//if(getContext().children().size() == 0) {
			for(int count = 0; count < maxNumChildren; count++) {
				createAndWatchChildActor(Props.create(Worker.class), "worker-" + (count+1));
			}
			
			scheduleHealthCheck();
			
			// we have the children created - Lets get them started too
			for(ActorRef child : getContext().getChildren()) {
				child.tell(Message.BEGIN, getSelf());
			}
		//}
	}
	
	private void bomb() {
		throw new RuntimeException("'Tracking Resource' not available.");
	}

}
