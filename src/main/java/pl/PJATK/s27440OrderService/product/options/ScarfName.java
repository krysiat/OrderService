package pl.PJATK.s27440OrderService.product.options;

public enum ScarfName {
    RoseScarf(60), OrchidScarf (65), DaisyScarf (70);
    private final double prize;

    ScarfName(double prize){
        this.prize = prize;
    }

    public double getPrize(){
        return prize;
    }
}
