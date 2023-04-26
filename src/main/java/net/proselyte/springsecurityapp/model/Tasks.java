package net.proselyte.springsecurityapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Generated;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Task
 */
@Generated("jsonschema2pojo")
public class Tasks {
    @SerializedName("uidDoc")
    @Expose
    private String uidDoc;
    @SerializedName("NameTask")
    @Expose
    private String NameTask;
    @SerializedName("TaskContent")
    @Expose
    private String taskContent;
    @SerializedName("TaskContentLVR")
    @Expose
    private String taskContentLVR;
    @SerializedName("TaskData")
    @Expose
    private String taskData;
    @SerializedName("TaskDataDone")
    @Expose
    private String taskDataDone;
    @SerializedName("TaskDeadline")
    @Expose
    private String taskDeadline;
    @SerializedName("TaskEmployee")
    @Expose
    private String taskEmployee;
    @SerializedName("TaskId")
    @Expose
    private String taskId;
    @SerializedName("TaskImportance")
    @Expose
    private String taskImportance;
    @SerializedName("TaskIntensity")
    @Expose
    private String taskIntensity;
    @SerializedName("TaskNumber")
    @Expose
    private String taskNumber;
    @SerializedName("TaskPartner")
    @Expose
    private String taskPartner;
    @SerializedName("TaskStatus")
    @Expose
    private String taskStatus;
    @SerializedName("TaskUrl")
    @Expose
    private String taskUrl;
    @SerializedName("TypeTask")
    @Expose
    private String typeTask;

    private MultipartFile file;


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Tasks() {
    }

    public String getNameTask() {
        return NameTask;
    }

    public void setNameTask(String nameTask) {
        NameTask = nameTask;
    }

    public String getUidDoc() {
        return uidDoc;
    }

    public void setUidDoc(String uidDoc) {
        this.uidDoc = uidDoc;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskContentLVR() {
        return taskContentLVR;
    }

    public void setTaskContentLVR(String taskContentLVR) {
        this.taskContentLVR = taskContentLVR;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public String getTaskDataDone() {
        return taskDataDone;
    }

    public void setTaskDataDone(String taskDataDone) {
        this.taskDataDone = taskDataDone;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public String getTaskEmployee() {
        return taskEmployee;
    }

    public void setTaskEmployee(String taskEmployee) {
        this.taskEmployee = taskEmployee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskImportance() {
        return taskImportance;
    }

    public void setTaskImportance(String taskImportance) {
        this.taskImportance = taskImportance;
    }

    public String getTaskIntensity() {
        return taskIntensity;
    }

    public void setTaskIntensity(String taskIntensity) {
        this.taskIntensity = taskIntensity;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getTaskPartner() {
        return taskPartner;
    }

    public void setTaskPartner(String taskPartner) {
        this.taskPartner = taskPartner;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(String typeTask) {
        this.typeTask = typeTask;
    }

//    public void changeStatus(String status) throws IOException{
//        HttpGet request = null;
//        if(status!=null && !status.isEmpty())//передаем выбранное состояние заявки - "на доработку"
//        {
//            if (status.equals("5"))
//            {
//                request = new HttpGet("http://192.168.1.224/franrit/hs/RitExchange/GetTestResult/"+this.uidDoc+"/5");
//            } else if (status.equals("8")) {
//                request = new HttpGet("http://192.168.1.224/franrit/hs/RitExchange/GetTestResult/"+this.uidDoc+"/8");
//            }
//            CloseableHttpClient client = HttpClientBuilder.create().build();
//            String encoding = Base64.getEncoder().encodeToString((forBasicAuth()[0] + ":" +forBasicAuth()[1]).getBytes());
//            String result;
//            request.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);//добавляем в заголовок запроса basic auth
//            CloseableHttpResponse response = client.execute(request);//выполняем запрос
//            try {
//                HttpEntity entity = response.getEntity();//получаем ответ от АПИ
//                result = EntityUtils.toString(entity);//засовываем ответ в строку
//                EntityUtils.consume(entity);//ответ парсим и кидаем в бд уид и все остальные введенные данные
//                System.out.println(result);
//                Gson g = new Gson();
//                //  userForm.setUidUser();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } finally {
//                response.close();
//            }
//        }
//    }
    public String[] forBasicAuth() throws IOException {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(in);//Выгружаем с настроек нужные для basic auth переменные
        }
        String username = props.getProperty("username");//Переменные для
        String password = props.getProperty("password");//basic auth
        String[] str = new String[]{username, password};
        return str;
    }

}
