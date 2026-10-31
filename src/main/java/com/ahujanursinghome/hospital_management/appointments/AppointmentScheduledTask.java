package com.ahujanursinghome.hospital_management.appointments;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppointmentScheduledTask extends Thread{
    private final AppointmentRepo _appointmentRepo;

    public AppointmentScheduledTask(AppointmentRepo appointmentRepo) {
        this._appointmentRepo = appointmentRepo;
    }
    // cron=second,min,hour,day,month,week) * means every day
    
    @Scheduled(cron ="0 0 8 * * *",zone = "Asia/Kolkata")
    public void checkPendingAppointment(){
        System.out.println("cron job is running");
    long count=_appointmentRepo.countPendinAppointment();
    System.out.println("Pendin appointments are:"+count);

    }
    


}
