package com.example.bottomtab;

public class MemoryCard {

    private int imageId;
    private boolean isFaceUp;
    private boolean isMatched;

    public MemoryCard(int imageiId, boolean isFaceup, boolean isMatched) {
        this.imageId = imageiId;
        this.isFaceUp = isFaceup;
        this.isMatched = isMatched;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageiId) {
        this.imageId = imageiId;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceup) {
        isFaceUp = faceup;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }


}
