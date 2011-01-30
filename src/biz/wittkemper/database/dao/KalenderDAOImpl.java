package biz.wittkemper.database.dao;

import biz.wittkemper.database.entity.Kalender;

public class KalenderDAOImpl extends AbstractDAOImpl<Kalender, Integer>
		implements KalenderDAO {

	@Override
	protected Class<Kalender> getDomainClass() {
		return Kalender.class;
	}

}
