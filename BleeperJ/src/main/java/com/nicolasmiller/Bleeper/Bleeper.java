package com.nicolasmiller.Bleeper;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.Control.Type;

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
			if(line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
				FloatControl masterGain = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
				masterGain.setValue((float) ((masterGain.getMaximum() + masterGain.getMinimum()) / 2.0));
			}
			
			line.open();
			line.start();
			int i = 10;
			
			while(i-- > 0) {
				line.write(WavetableGenerator.noise(line.getBufferSize()), 0, line.getBufferSize());
			}
			
			i = 10;
			
			while(i-- > 0) {
				line.write(WavetableGenerator.lame_sawtooth(line.getBufferSize()), 0, line.getBufferSize());
			}
		
			i = 10;
			
			while(i-- > 0) {
				line.write(WavetableGenerator.lame_rect(line.getBufferSize()), 0, line.getBufferSize());
			}
			
			line.drain();
			line.stop();
			line.close();
			line = null;
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
