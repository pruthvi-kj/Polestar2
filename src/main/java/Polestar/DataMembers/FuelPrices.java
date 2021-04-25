package Polestar.DataMembers;

public class FuelPrices {
    public float electricityPrice;
    public float fuelPrice;
    public Long yearCostForPolestar2;
    public Long yearCostForFuelCar;
    public Long monthCostForPolestar2;
    public Long monthCostForFuelCar;
    public Long yearEstimatedFuelSavings;
    public Long monthEstimatedFuelSavings;

    public FuelPrices(float electricityPrice, float fuelPrice) {
        this.electricityPrice=electricityPrice;
        this.fuelPrice=fuelPrice;
    }
    public FuelPrices(Long yearCostForPolestar2,Long yearCostForFuelCar,Long yearEstimatedFuelSavings,Long monthCostForPolestar2,Long monthCostForFuelCar, Long monthEstimatedFuelSavings) {
        this.yearCostForFuelCar= yearCostForFuelCar;
        this.yearCostForPolestar2=yearCostForPolestar2;
        this.monthCostForPolestar2=monthCostForPolestar2;
        this.monthCostForFuelCar=monthCostForFuelCar;
        this.yearEstimatedFuelSavings=yearEstimatedFuelSavings;
        this.monthEstimatedFuelSavings=monthEstimatedFuelSavings;

    }

    public FuelPrices() {

    }
}
