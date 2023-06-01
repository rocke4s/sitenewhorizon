//package net.proselyte.springsecurityapp.model;
//
//import org.springframework.context.annotation.Primary;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "task_list", uniqueConstraints = @UniqueConstraint( columnNames={"number_task"} ) )
//public class TaskList {
//    @Column(name = "number_task")
//    private Long numberTask;
//    @Column(name = "link")
//    private String link;
//    @Column(name = "task_partner")
//    private String taskPartner;
//    @Column(name = "status")
//    private String status;
//    @Column(name = "type_task")
//    private String typeTask;
//    @Column(name = "importance")
//    private String importance;
//    @Column(name = "attachment")
//    private String attachment;
//    @Column(name = "name_task")
//    private String nameTask;
//    @Column(name = "date_exec")
//    private String dateExec;
//    @Column(name = "labor_costs")
//    private String laborCosts;
//    @Column(name = "id")
//    private String id;
//    @Column(name = "date_end")
//    private String dateEnd;
//    @Column(name = "content_luvr")
//    private String contentLuvr;
//    @Column(name = "date_create")
//    private String dateCreate;
//    @Column(name = "department")
//    private String department;
//    @Column(name = "uid_doc")
//    private String uidDoc;
//    @Column(name = "uid_user")
//    private String uidUser;
//    @Column(name = "date_upd")
//    private String dateUpd;
//
//    public Long getNumberTask() {
//        return numberTask;
//    }
//
//    public void setNumberTask(Long numberTask) {
//        this.numberTask = numberTask;
//    }
//
//    public String getLink() {
//        return link;
//    }
//
//    public void setLink(String link) {
//        this.link = link;
//    }
//
//    public String getTaskPartner() {
//        return taskPartner;
//    }
//
//    public void setTaskPartner(String taskPartner) {
//        this.taskPartner = taskPartner;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getTypeTask() {
//        return typeTask;
//    }
//
//    public void setTypeTask(String typeTask) {
//        this.typeTask = typeTask;
//    }
//
//    public String getImportance() {
//        return importance;
//    }
//
//    public void setImportance(String importance) {
//        this.importance = importance;
//    }
//
//    public String getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(String attachment) {
//        this.attachment = attachment;
//    }
//
//    public String getNameTask() {
//        return nameTask;
//    }
//
//    public void setNameTask(String nameTask) {
//        this.nameTask = nameTask;
//    }
//
//    public String getDateExec() {
//        return dateExec;
//    }
//
//    public void setDateExec(String dateExec) {
//        this.dateExec = dateExec;
//    }
//
//    public String getLaborCosts() {
//        return laborCosts;
//    }
//
//    public void setLaborCosts(String laborCosts) {
//        this.laborCosts = laborCosts;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getDateEnd() {
//        return dateEnd;
//    }
//
//    public void setDateEnd(String dateEnd) {
//        this.dateEnd = dateEnd;
//    }
//
//    public String getContentLuvr() {
//        return contentLuvr;
//    }
//
//    public void setContentLuvr(String contentLuvr) {
//        this.contentLuvr = contentLuvr;
//    }
//
//    public String getDateCreate() {
//        return dateCreate;
//    }
//
//    public void setDateCreate(String dateCreate) {
//        this.dateCreate = dateCreate;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }
//
//    public String getUidDoc() {
//        return uidDoc;
//    }
//
//    public void setUidDoc(String uidDoc) {
//        this.uidDoc = uidDoc;
//    }
//
//    public String getUidUser() {
//        return uidUser;
//    }
//
//    public void setUidUser(String uidUser) {
//        this.uidUser = uidUser;
//    }
//
//    public String getDateUpd() {
//        return dateUpd;
//    }
//
//    public void setDateUpd(String dateUpd) {
//        this.dateUpd = dateUpd;
//    }
//}
