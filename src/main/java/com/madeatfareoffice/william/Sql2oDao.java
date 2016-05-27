package com.madeatfareoffice.william;

import com.madeatfareoffice.william.objects.Action;
import com.madeatfareoffice.william.objects.OtaEquipment;
import com.sun.tools.internal.xjc.model.Model;
import org.joda.time.LocalDate;
import org.sql2o.*;

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
    public UUID createOtaEquipment(String otaCode, String description) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID otaUuid = uuidGenerator.generate();
            conn.createQuery("insert into ota_equipments(ota_uuid, ota, description) VALUES (:ota_uuid, :ota, :description)")
                    .addParameter("ota_uuid", otaUuid)
                    .addParameter("ota", otaCode)
                    .addParameter("description", description)
                    .executeUpdate();
            conn.commit();
            return otaUuid;
        }
    }

    /**
     * Fetch all OTA equipments from database.
     * @return
     */
    public List<OtaEquipment> getAllOtaEquipments() {
        try (Connection conn = sql2o.open()) {
            List<OtaEquipment> equipments = conn.createQuery("select * from ota_equipments")
                    .executeAndFetch(OtaEquipment.class);
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
    public List<Action> getAllActions() {
        try (Connection conn = sql2o.open()) {
            List<Action> actions = conn.createQuery("select * from actions")
                    .executeAndFetch(Action.class);
            return actions;
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

