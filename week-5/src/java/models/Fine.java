/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import repositories.Entity;

/**
 *
 * @author shai
 */
public class Fine implements Entity<String>{
    
    public String id;
    public String studentId;
    public long createdDate;
    public int lateInDays;
    public boolean paid;

    public Fine(String id, String studentId, long createdDate, int lateInDays, boolean paid) {
        this.id = id;
        this.studentId = studentId;
        this.createdDate = createdDate;
        this.lateInDays = lateInDays;
        this.paid = paid;
    }
    
    public Fine(String studentId, long createdDate, int lateInDays, boolean paid) {
        this.id = studentId + "-" + createdDate;
        this.studentId = studentId;
        this.createdDate = createdDate;
        this.lateInDays = lateInDays;
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Fine{" + "studentId=" + studentId + ", createdDate=" + createdDate + ", lateInDays=" + lateInDays + '}';
    }

    @Override
    public String getIdValue() {
        return this.id;
    }

    @Override
    public String getIdKey() {
        return "id";
    }
}
