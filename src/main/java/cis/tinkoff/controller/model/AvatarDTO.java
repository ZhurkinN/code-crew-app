package cis.tinkoff.controller.model;

public class AvatarDTO {
    private String path;

    public AvatarDTO(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
