public class FishType {
    private final String imagePath;
    private final double scale;
    
    public FishType(String imagePath, double scale) {
        this.imagePath = imagePath;
        this.scale = scale;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getScale() {
        return scale;
    }
}
