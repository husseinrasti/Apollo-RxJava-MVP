package ir.husseinrasti.app.apollorxjava.data;

public class PostEntity {

    private String id;
    private String type;
    private String title;
    private String desc;
    private String imagUrl;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc( String desc ) {
        this.desc = desc;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl( String imagUrl ) {
        this.imagUrl = imagUrl;
    }
}
