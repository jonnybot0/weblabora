package com.philihp.weblabora.model;

import static com.philihp.weblabora.model.Wheel.Position.*;

import com.philihp.weblabora.model.Wheel.Position;

public class Token implements Cloneable {
	private Position position;
	protected Token() {
		this.position = M;
	}
	protected int value() {
		return 0;
	}
	public int take(Wheel wheel) {
		int i = wheel.arm.position.ordinal() - position.ordinal();
		if(i < 0) i += wheel.armValues.length;
		position = wheel.arm.position;
		return wheel.armValues[i];
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Token newToken = (Token)super.clone();
		newToken.position = this.position;
		return newToken;
	}
}
