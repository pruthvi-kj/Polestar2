package UtilsTest;

public enum EndPoints {
    Polestar2("/polestar-2"),
    ServiceAndAssistance("/service-and-assistance"),
    ElectricDriving("/electric-driving"),
    BuyingProcess("/buying-process");

    private String endPoint;

    EndPoints(String resource) {
        this.endPoint = resource;
    }

    public String getEndPoint() {
        return endPoint;
    }

}

