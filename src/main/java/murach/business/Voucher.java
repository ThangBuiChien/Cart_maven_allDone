package murach.business;

public class Voucher {

    private String voucherCode;
    private double discountPercentage;

    public Voucher(String voucherCode, double discountPercentage) {
        this.voucherCode = voucherCode;
        this.discountPercentage = discountPercentage;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public boolean isVoucherValid(String userInput) {
        return userInput != null && userInput.equals(voucherCode);
    }

    public double applyDiscount(double totalPrice) {
        return totalPrice - (totalPrice * (discountPercentage / 100.0));
    }
}
