package com.philihp.weblabora.model;

public class Clergyman {
	
	public enum Type {
		LAYBROTHER, PRIOR;
	}
	
	private Player owner;
	
	private Type type;
	
	protected Terrain location;
	
	public Clergyman(Player owner, Type type) {
		this.owner = owner;
		this.type = type;
	}

	protected Player getOwner() {
		return owner;
	}

	public Type getType() {
		return type;
	}

	protected Terrain getLocation() {
		return location;
	}
	
	public void clearLocation() {
		if(this.location != null) {
			Terrain oldLocation = this.location;
			this.location = null;
			oldLocation.getErection().clearClergyman();
		}
	}

	protected void setLocation(Terrain location) throws WeblaboraException {
		if (location.getErection().getClergyman() != null)
			throw new WeblaboraException("The building "
					+ location.getErection() + " already has a clergyman "
					+ location.getErection().getClergyman().getType()); 
		
		this.location = location;
		location.getErection().setClergyman(this);
	}

}
