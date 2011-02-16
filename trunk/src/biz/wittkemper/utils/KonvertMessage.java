package biz.wittkemper.utils;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.HeadlessException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TimerTask;

import javax.swing.JLabel;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import biz.wittkemper.database.dao.DAOFactory;
import biz.wittkemper.database.dao.SessionFactotyUtil;
import biz.wittkemper.database.entity.Alarmierung;
import biz.wittkemper.database.entity.Monitord_POSAC;
import biz.wittkemper.gui.MeldungsMelder;

public class KonvertMessage extends TimerTask{

	JLabel status ;
	MeldungsMelder melder ;
	
	public KonvertMessage(JLabel status, MeldungsMelder meldungsMelder){
		this.status = status;
		this.melder = meldungsMelder;
	}
	
	private void setzeText(String text){
		if(status != null)
			status.setText(text);
	}
	@Override
	public void run(){
		setzeText("Suche neue Meldungen.....");
		List<Monitord_POSAC> meldungen = DAOFactory.getInstance().getMonitordPOSACDAO().getNewMessages();
		setzeText(meldungen.size() + " Meldungen gefunden.");
		for (Monitord_POSAC mp: meldungen){
			Alarmierung alarmierung = checkAlarmierung(mp);
			if(alarmierung != null){
				if (alarmierung.getEot() == false){
					if(alarmierung.getText().length() < mp.getText().length()){
						alarmierung.setText(mp.getText());
						chekEOT(mp, alarmierung);
						DAOFactory.getInstance().getAlarmierungDAO().update(alarmierung);
					}
				}
			}else{
				setzeText("Neue Meldung gefunden");
				trageAlarmEin(mp);
			}
			DAOFactory.getInstance().getMonitordPOSACDAO().delete(mp);
		}
		Calendar cal = new GregorianCalendar( TimeZone.getTimeZone("ECT") );
	    DateFormat formater;
		formater = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM );
		setzeText("Durchlauf beendet...." + formater.format(cal.getTime()));
	}

	public void trageAlarmEin(Monitord_POSAC mp) {
		Alarmierung alarmierung = new Alarmierung();
		alarmierung.setKennung(DAOFactory.getInstance().getKennungDAO().load(Long.parseLong(mp.getKennung())));
		if (alarmierung.getKennung().getAusblenden()== true){
			alarmierung.setText("");
		}else {
			alarmierung.setText(mp.getText());
		}
		alarmierung.setUhrzeit(mp.getUhrzeit());
		chekEOT(mp, alarmierung);
		alarmierung.setStatus(DAOFactory.getInstance().getPosacStatusDAO().findByStatus(Integer.parseInt(mp.getSub())));
		
		DAOFactory.getInstance().getAlarmierungDAO().save(alarmierung);
		melder.setAenderung(alarmierung);
		
		URL url = this.getClass().getResource("Einsatz.wav");
		try {
			AudioClip sound = new Applet().newAudioClip(url);
			sound.play();
		} catch (Exception e) {
			System.out.println("Sound läuft ....");
			e.printStackTrace();
		} 
		
		
	}
	
	private void chekEOT(Monitord_POSAC mp, Alarmierung alarmierung) {
		if (mp.getText().indexOf("<EOT>") > 0){
			alarmierung.setText(alarmierung.getText().replaceAll("<EOT>", ""));
			alarmierung.setEot(true);
		}else{
			alarmierung.setEot(false);
		}
	}
	private Alarmierung checkAlarmierung(Monitord_POSAC mp) {
		Calendar calendar = new GregorianCalendar();
		
		StringBuffer hql = new StringBuffer(" From Alarmierung a Where ");
		
		calendar.setTime(mp.getUhrzeit());
		calendar.add(Calendar.MINUTE, -2);
		Date minDate = calendar.getTime();
		calendar.add(Calendar.MINUTE, +4);
		Date maxDate = calendar.getTime();
		
		
		hql.append("a.uhrzeit >= :startDate");

		hql.append(" AND ");
		hql.append("a.uhrzeit <= :endDate");
		
		hql.append(" AND a.kennung.ric = " + mp.getKennung());
		hql.append(" AND a.status.posac_status = " + mp.getSub());
		
		Session session = SessionFactotyUtil.getInstance().getCurrentSession();
		Transaction trx = session.beginTransaction();
		
		Query query = session.createQuery(hql.toString());
		query.setParameter("startDate", minDate);
		query.setParameter("endDate", maxDate);
		
		List<Alarmierung> list = query.list();
		trx.commit();
		if (list.size()> 0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
