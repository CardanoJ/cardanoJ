package com.org.cardanoJ.blockfrost.exception;

public class BlockFrostConfigurationException extends RuntimeException{
	 public BlockFrostConfigurationException(String message) {
	        super(message);
	    }

	    public BlockFrostConfigurationException(String message, Exception exception) {
	        super(message, exception);
	    }
}
