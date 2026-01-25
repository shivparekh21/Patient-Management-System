package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import patient.events.PatientEvent;

@Component
public class KafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    // define message type, the message[key:String, value:byte[]] we send kafka topic from this producers
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPatientEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED") // Approach we could use to add further sub-category to a message within a topic
                .build();

        try{
            kafkaTemplate.send("patient", event.toByteArray());
        }catch (Exception e){
            log.error("Error sending PatientCreated event: {}"+ event);
        }
    }

}
