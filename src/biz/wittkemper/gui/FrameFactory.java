package biz.wittkemper.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;

public class FrameFactory {

	List<JInternalFrame> frames = new ArrayList<JInternalFrame>();
	private int currentFrame=0;
	
	public FrameFactory(){
		frames.add(null);
		frames.add(new FStatistik());
		frames.add(new FKalender());
		currentFrame =0;
	}
	
	public JInternalFrame getNextFrame(){
		
		currentFrame ++;
		if (currentFrame > frames.size()){
			currentFrame =0;
		}
		switch (currentFrame)
		{
			case 0: return null;
			
			case 1: return new FKalender();
			
			case 2: return new FStatistik();
			
			default: return null;
		}
	}
}