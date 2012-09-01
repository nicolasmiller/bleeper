package com.nicolasmiller.Bleeper;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioSystemUtility {
	public static String indent(int numTabs) {
		String indent = "";
		while(numTabs-- > 0) {
			indent += "\t";
		}
		return indent; 
	}
	
	public static void printSourceLineInfo(SourceDataLine s) {
		System.out.println("Buffer size: " + s.getBufferSize());
		System.out.println("Level: " + s.getLevel());
		System.out.println("Format: " + s.getFormat());
		System.out.println("Frame Position: " + s.getFramePosition());
		System.out.println("Long Frame Position: " + s.getLongFramePosition());
		System.out.println("Microsecond Position: " + s.getMicrosecondPosition());
		printControlInfo(s.getControls(), 1);
	}
	
	private static void printControlInfo(Control[] controls, int numTabs) {
		for(Control c : controls) {
				System.out.println(indent(numTabs) + c.getType().toString());
		}
	}
	
	private static void printMixerInfo(Mixer.Info[] mixerInfos, AudioFormat format) {
		int i = 0;
		for(Mixer.Info info : mixerInfos) {
			System.out.println("Mixer " + i);
			System.out.println(info.getName());
			System.out.println(info.getVendor());
			System.out.println(info.getVersion());
			System.out.println(info.getDescription());
			
			Mixer mixer = AudioSystem.getMixer(info);
			DataLine.Info targetLineInfo = new DataLine.Info(TargetDataLine.class, 
			    format); 
			printMaxLines(mixer, targetLineInfo);
			
			DataLine.Info sourceLineInfo = new DataLine.Info(SourceDataLine.class, 
			    format); 
			printMaxLines(mixer, sourceLineInfo);
			
			System.out.println();
			
			i++;
		}
	}
	
	private static void printMaxLines(Mixer mixer, DataLine.Info lineInfo) {
			System.out.println("Max " + lineInfo.getLineClass().getName() + " lines:" + mixer.getMaxLines(lineInfo));
	}
	
	public static Mixer[] getMixers() {
		Mixer.Info[] infos = AudioSystem.getMixerInfo();
		Mixer[] mixers = new Mixer[infos.length];
		for(int i = 0; i < infos.length; i++) {
			mixers[i] = AudioSystem.getMixer(infos[i]);
		}
		return mixers;
	}
	
	public static void printAudioSystemInfo(AudioFormat format) {
		printMixerInfo(AudioSystem.getMixerInfo(), format);
	}
}