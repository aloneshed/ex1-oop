package ex1.src;

public class OrderedPair <L, R> {
    private L left;
    private R right;

    public OrderedPair(){
        this.left = null;
        this.right = null;
    }

    public OrderedPair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return this.left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return this.right;
    }

    public void setRight(R right) {
        this.right = right;
    }
}
