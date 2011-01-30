package biz.wittkemper.database.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import biz.wittkemper.database.entity.Alarmierung;
import biz.wittkemper.database.entity.Kennung;
import biz.wittkemper.database.entity.KennungStatisik;

public class KennungDAOImpl extends AbstractDAOImpl<Kennung, Long> implements
		KennungDAO {

	public List<KennungStatisik> getStatistikLaufendesJahr(){
		
		List<KennungStatisik> auswertung = new ArrayList<KennungStatisik>();
		
		String hql ="";
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0);
		Date startTime = calendar.getTime();
		
		hql += " FROM Alarmierung a Where a.uhrzeit >=:startTime ";
		hql += " AND a.kennung.ric=:ric ";
		hql += " ORDER BY a.uhrzeit DESC";
		
		List<Kennung> kennung = DAOFactory.getInstance().getKennungDAO().findByQueryString("From Kennung");
		
		
		for(Kennung k: kennung){
			KennungStatisik statisik = new KennungStatisik();
			statisik.setKennung(k);
			
			Session session = SessionFactotyUtil.getInstance().getCurrentSession();
			Transaction trx = session.beginTransaction();
			
			Query query = session.createQuery(hql.toString());
			query.setParameter("startTime", startTime);
			query.setParameter("ric", k.getRic());
			
			List<Alarmierung> alarme = query.list();
			
			
			if (alarme.size()>0){
				statisik.setAnzahlMeldungen(alarme.size());
				statisik.setLetzteMeldung(alarme.get(0).getUhrzeit());
			}else{
				statisik.setAnzahlMeldungen(0);
				statisik.setLetzteMeldung(null);
			}
			trx.commit();
			auswertung.add(statisik);
		}
		
		return auswertung;
	}
	
	@Override
	protected Class<Kennung> getDomainClass() {
		return Kennung.class;
	}

}
