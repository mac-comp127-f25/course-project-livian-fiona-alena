/**
 *  Class representing a type of fish with its image path and scale.
 */
public class FishType {
    private final String imagePath;
    private final double scale;
    
    /**
     * Constructs a FishType description.
     * 
     * @param imagePath The file path to the fish's image.
     * @param scale     The scale factor for the fish's size.
    */
    public FishType(String imagePath, double scale) {
        this.imagePath = imagePath;
        this.scale = scale;
    }

    /**
     * @return The image path of the fish type.
    */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @return The scale of the fish type.
    */
    public double getScale() {
        return scale;
    }
}
