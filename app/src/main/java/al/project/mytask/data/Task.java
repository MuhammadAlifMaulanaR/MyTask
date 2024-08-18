package al.project.mytask.data;

public class Task {
    private String taskId;
    private String taskName;
    private String category;

    public Task(){

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String calender;
    public String description;

    public String getCalender() {
        return calender;
    }

    public void setCalender(String calender) {
        this.calender = calender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(String taskId, String taskName, String category, String deadline, String description){
        this.taskId = taskId;
        this.taskName = taskName;
        this.category =category;
        this.calender = deadline;
        this.description = description;
    }
}
