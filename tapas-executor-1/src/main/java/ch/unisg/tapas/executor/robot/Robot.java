package ch.unisg.tapas.executor.robot;

public class Robot {
    private int elbowValue;
    private int wristAngleValue;
    private int wristRotationValue;
    private int gripperValue;

    public Robot (int elbow,
                  int angle,
                  int rotation,
                  int gripper) {
        this.elbowValue = elbow;
        this.wristAngleValue = angle;
        this.wristRotationValue = rotation;
        this.gripperValue = gripper;
    }

    public int getElbowValue() {
        return elbowValue;
    }

    public void setElbowValue(int elbowValue) {
        this.elbowValue = elbowValue;
    }

    public int getWristAngleValue() {
        return wristAngleValue;
    }

    public void setWristAngleValue(int wristAngleValue) {
        this.wristAngleValue = wristAngleValue;
    }

    public int getWristRotationValue() {
        return wristRotationValue;
    }

    public void setWristRotationValue(int wristRotationValue) {
        this.wristRotationValue = wristRotationValue;
    }

    public int getGripperValue() {
        return gripperValue;
    }

    public void setGripperValue(int gripperValue) {
        this.gripperValue = gripperValue;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "elbowValue=" + elbowValue +
                ", wristAngleValue=" + wristAngleValue +
                ", wristRotationValue=" + wristRotationValue +
                ", gripperValue=" + gripperValue +
                '}';
    }
}
