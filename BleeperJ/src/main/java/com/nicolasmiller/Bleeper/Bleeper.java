package com.nicolasmiller.Bleeper;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

public class Bleeper {
	public static void main(String[] args) {
		AudioFormat format = new AudioFormat(44100, 16, 2, true, false);
		
		AudioSystemUtility.printAudioSystemInfo(format);
		
		Mixer mixer = AudioSystemUtility.getMixers()[0];
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, 
			    format);
		try {
			SourceDataLine line = (SourceDataLine) mixer.getLine(info);
			AudioSystemUtility.printSourceLineInfo(line);
			byte[] buffer = new byte[line.getBufferSize()];
			for(int i = 0; i < buffer.length; i++) {
				int b = (int) Math.floor(Math.random() * 255) - 128;
				buffer[i] = (byte) b;
			}
			line.open();
			line.start();
			while(true) {
				line.write(buffer, 0, line.getBufferSize());
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
