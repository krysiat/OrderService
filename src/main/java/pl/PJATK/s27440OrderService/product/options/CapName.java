package pl.PJATK.s27440OrderService.product.options;

public enum CapName {
    RoseCap(80), OrchidCap(85), DaisyCap(90);
    private final double prize;

    CapName(double prize) {
        this.prize = prize;
    }

    public double getPrize() {
        return prize;
    }
}
