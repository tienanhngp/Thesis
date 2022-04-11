package thesis.readsensorapp;

public class Sensor {
    private int resouceImage;
    private String sensorName;
    private String sensorValue;

    public Sensor(int resouceImage, String sensorName, String sensorValue) {
        this.resouceImage = resouceImage;
        this.sensorName = sensorName;
        this.sensorValue = sensorValue;
    }

    public int getResouceImage() {
        return resouceImage;
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setResouceImage(int resouceImage) {
        this.resouceImage = resouceImage;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }
}
