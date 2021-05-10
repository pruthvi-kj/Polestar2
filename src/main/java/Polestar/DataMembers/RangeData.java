package Polestar.DataMembers;

public class RangeData {
    public int numberOfCharge;
    public int rangePercentage;
    public int miles;

    public RangeData(int numberOfCharge, int percentage, int miles) {
        this.numberOfCharge = numberOfCharge;
        this.rangePercentage = percentage;
        this.miles = miles;
    }

    public RangeData() {
    }
}
