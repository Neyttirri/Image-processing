// BV Ue1 WS2019/20 Vorgabe
//
// Copyright (C) 2019 by Klaus Jung
// All rights reserved.
// Date: 2019-09-28

package bv_ws1920;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class RasterImage {
	
	private static final int gray  = 0xffa0a0a0;

	public int[] argb;	// pixels represented as ARGB values in scanline order
	public int width;	// image width in pixels
	public int height;	// image height in pixels
	private Random rand = new Random();
	
	public RasterImage(int width, int height) {
		// creates an empty RasterImage of given size
		this.width = width;
		this.height = height;
		argb = new int[width * height];
		Arrays.fill(argb, gray);
	}
	
	public RasterImage(RasterImage src) {
		// copy constructor
		this.width = src.width;
		this.height = src.height;
		argb = src.argb.clone();
	}
	
	public RasterImage(File file) {
		// creates an RasterImage by reading the given file
		Image image = null;
		if(file != null && file.exists()) {
			image = new Image(file.toURI().toString());
		}
		if(image != null && image.getPixelReader() != null) {
			width = (int)image.getWidth();
			height = (int)image.getHeight();
			argb = new int[width * height];
			image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
		} else {
			// file reading failed: create an empty RasterImage
			this.width = 256;
			this.height = 256;
			argb = new int[width * height];
			Arrays.fill(argb, gray);
		}
	}
	
	public RasterImage(ImageView imageView) {
		// creates a RasterImage from that what is shown in the given ImageView
		Image image = imageView.getImage();
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		argb = new int[width * height];
		image.getPixelReader().getPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
	}
	
	public void setToView(ImageView imageView) {
		// sets the current argb pixels to be shown in the given ImageView
		if(argb != null) {
			WritableImage wr = new WritableImage(width, height);
			PixelWriter pw = wr.getPixelWriter();
			pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), argb, 0, width);
			imageView.setImage(wr);
		}
	}
	
	
	// image point operations to be added here
	
	public void convertToGray() {
		// convert the image to grayscale
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				int pos = y*width + x;
				int value = argb[pos];  
				
				int r = (value >> 16) & 0xff;
				int g = (value >>  8) & 0xff;
				int b =  value        & 0xff;
				
				double Y = 0.299 * r + 0.587 * g + 0.114 * b;
				int rn = (int) Y;
				int gn = (int) Y;
				int bn = (int) Y;
				argb[pos] = (0xFF<<24) | (rn<<16) | (gn<<8) | bn;
		
			}
		}
	}
	
	/**
	 * @param quantity The fraction of pixels that need to be modified
	 * @param strength The brightness to be added or subtracted from a pixel's gray level
	 */
	public void addNoise(double quantity, int strength) {
		int numOfPix = (int) (argb.length * quantity);
		for (int i = 0; i < numOfPix; i++) {
			int pos = randomNum(0, argb.length -1);
			int val = argb[pos];
			int b =  val & 0xff;
			if(i % 2 == 0) {
				b += strength;
			}
			else{
				b -= strength;
			}
			b = normalize(b);
			argb[pos] = (0xFF<<24) | (b<<16) | (b<<8) | b;
		}
	}
	
	private int randomNum(int min, int max){
		// generates random number within a specific range
		return rand.nextInt((max - min) + 1) + min;
	}
	
	int normalize(int pixelValue){
		return Math.max(0, Math.min(pixelValue , 255));
	}
	
	
	

}
