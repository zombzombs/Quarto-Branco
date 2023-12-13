package com.tcc.entities;

import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;

public class Text extends Entity{
	public static String title;
	public static String text;
	
	public Text(double x, double y, int width, int height, BufferedImage sprite, String title, String text) {
		super(x, y, width, height, sprite);
		Text.text = text;
		Text.title = title;
	}
}
