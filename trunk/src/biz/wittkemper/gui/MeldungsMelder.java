package biz.wittkemper.gui;

import java.util.Observable;

import biz.wittkemper.database.entity.Alarmierung;

public class MeldungsMelder extends Observable {
	
	public MeldungsMelder(){
		super();
	}
	
	public void setAenderung(Alarmierung alarmierung){
		super.setChanged();
		super.notifyObservers(alarmierung);
	}
	public void setAenderung(String text){
		super.setChanged();
		super.notifyObservers(text);
	}
}
