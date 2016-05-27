package com.madeatfareoffice.william;

import com.madeatfareoffice.william.objects.ActionResponse;
import com.madeatfareoffice.william.objects.OtaEquipmentResponse;
import com.madeatfareoffice.william.objects.RecommendResponse;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.UUID;

public class Sql2oDao {

    private Sql2o sql2o;
    private RandomUuidGenerator uuidGenerator;

    public Sql2oDao(Sql2o sql2o) {
        this.sql2o = sql2o;
        uuidGenerator = new RandomUuidGenerator();
    }

    /**
     * Persists a OTA equipment to database.
     * @param otaCode
     * @param description
     * @return
     */
    public String createOtaEquipment(String otaCode, String description) {
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery("insert into ota_equipment(ota, description) VALUES (:ota, :description)")
                    .addParameter("ota", otaCode)
                    .addParameter("description", description)
                    .executeUpdate();
            conn.commit();
            return otaCode;
        }
    }

    /**
     * Fetch all OTA equipments from database.
     * @return
     */
    public List<OtaEquipmentResponse> getAllOtaEquipments() {
        try (Connection conn = sql2o.open()) {
            List<OtaEquipmentResponse> equipments = conn.createQuery("select * from ota_equipment")
                    .executeAndFetch(OtaEquipmentResponse.class);
            return equipments;
        }
    }

    /**
     * Persists a OTA equipment to database.
     * @param otaCode
     * @param plo
     * @param localDate
     * @return
     */
    public UUID createAction(String otaCode, String plo, LocalDate localDate) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID otaUuid = uuidGenerator.generate();
            conn.createQuery("insert into actions(action_uuid, ota, plo, date) VALUES (:action_uuid, :ota, :plo, :date)")
                    .addParameter("action_uuid", otaUuid)
                    .addParameter("ota", otaCode)
                    .addParameter("plo", plo)
                    .addParameter("date", localDate.toDateTimeAtStartOfDay().toDate())
                    .executeUpdate();
            conn.commit();
            return otaUuid;
        }
    }

    /**
     * Fetch all OTA equipments from database.
     * @return
     */
    public List<ActionResponse> getAllActions() {
        try (Connection conn = sql2o.open()) {
            List<ActionResponse> actions = conn.createQuery("select * from actions")
                    .executeAndFetch(ActionResponse.class);
            return actions;
        }
    }

	public List<RecommendResponse> getRecommendations(YearMonth yearMonth) {
		DateTime start = yearMonth.toLocalDate(1).toDateTimeAtStartOfDay();
		DateTime end = start.plusMonths(1);
		try (Connection conn = sql2o.open()) {
			List<RecommendResponse> recommendations = conn.createQuery(
					"select ota, count(*) as count " +
					"from actions " +
					"where " +
						"date >= :start " +
						"and date < :end " +
					"group by ota " +
					"order by count(*) desc " +
					"limit 5")
				.addParameter("start", start)
				.addParameter("end", end)
				.executeAndFetch(RecommendResponse.class);
			return recommendations;
		}
	}
    
    /**
     * Our generator of UUIDs, just random.
     *
     * @author ftomassetti
     * @since Mar 2015
     */
    public class RandomUuidGenerator {
        public UUID generate() {
            return UUID.randomUUID();
        }
    }
}

