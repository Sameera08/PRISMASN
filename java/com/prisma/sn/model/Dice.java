package com.prisma.sn.model;

import org.apache.commons.lang3.RandomUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Dice {
	private int minValue;
	private int maxValue;
	@Setter
	private int dice1Value;
	@Setter
	private int dice2Value;
	@Setter
	private int resultVal;

	public Dice(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;

	}

	public void roll() {
		setDice1Value(RandomUtils.nextInt(minValue, maxValue + 1));
		setDice2Value(RandomUtils.nextInt(minValue, maxValue + 1));
	}
}
