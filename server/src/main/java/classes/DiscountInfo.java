package classes;

public class DiscountInfo {

    private int right;
    private int left;
    private int discount;

    public DiscountInfo(int right, int left, int discount) {
        this.right = right;
        this.left = left;
        this.discount = discount;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
