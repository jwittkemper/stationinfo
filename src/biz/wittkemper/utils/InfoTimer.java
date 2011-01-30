package biz.wittkemper.utils;

import java.util.TimerTask;

import biz.wittkemper.gui.MeldungsMelder;

public class InfoTimer extends TimerTask {

	MeldungsMelder melder;
	
	public InfoTimer(MeldungsMelder meldungsMelder){
		this.melder = meldungsMelder;
	}
	@Override
	public void run() {
		melder.setAenderung("NEXTFRAME");

	}

}
