package com.nicolasmiller.Bleeper;

public class WavetableGenerator {

	// right now these assume an audio format of
	// PCM_SIGNED 44100.0 Hz, 16 bit, stereo, 4 bytes/frame, little-endian
	// and are merely what I could think of without consulting anything
	//
	// need to read up on proper waveform generation...
	
	public static byte[] noise(int length) {
		byte[] buffer = new byte[length];
		for(int i = 0; i < buffer.length; i++) {
			int b = (int) Math.floor(Math.random() * 255) - 128;
			buffer[i] = (byte) b;
		}	
		return buffer;
	}
	
	public static byte[] lame_sawtooth(int length) {
		byte[] buffer = new byte[length];
		short val = Short.MIN_VALUE;
		for(int i = 0; i < buffer.length; i += 4) {
			int little = val & 0x0F;
			int big = val >> 8;
			buffer[i] = (byte) little; 
			buffer[i + 1] = (byte) big; 
			buffer[i + 2] = (byte) little; 
			buffer[i + 3] = (byte) big;
			val += 100;
		}	
		return buffer;	
	}
	
	public static byte[] lame_rect(int length) {
		byte[] buffer = new byte[length];
		short val = Short.MIN_VALUE;
		for(int i = 0; i < buffer.length; i += 4) {
			int little = val & 0x0F;
			int big = val >> 8;
			buffer[i] = (byte) little; 
			buffer[i + 1] = (byte) big; 
			buffer[i + 2] = (byte) little; 
			buffer[i + 3] = (byte) big;
			if((i % 1000) == 0) {
				val = val == Short.MIN_VALUE ? Short.MAX_VALUE : Short.MIN_VALUE;
			}
		}	
		return buffer;		
	}
	
	public static byte[] naive_sine(int length, int f) {
		byte[] buffer = new byte[length];
		for(int i = 0; i < buffer.length; i += 4) {
			double t = (buffer.length / 44100.0) * ((double) i / buffer.length);
			double sample = Short.MAX_VALUE * Math.sin(2 * Math.PI * f * t);
			int val = (int) sample;
			int little = val & 0x0F;
			int big = val >> 8;
			buffer[i] = (byte) little; 
			buffer[i + 1] = (byte) big; 
			buffer[i + 2] = (byte) little; 
			buffer[i + 3] = (byte) big;
		}
		return buffer;
	}
}
