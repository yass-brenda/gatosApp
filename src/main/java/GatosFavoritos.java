public class GatosFavoritos {
    private String id;
    private String imageId;
    private String apiKey = "c154f849-b5df-4b5a-a721-df14505cdcfb";
    ImageX image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageID() {
        return imageId;
    }

    public void setImageID(String imageID) {
        this.imageId = imageID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ImageX getImage() {
        return image;
    }

    public void setImage(ImageX image) {
        this.image = image;
    }
}
