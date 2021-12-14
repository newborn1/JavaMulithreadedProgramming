package dataprocessing;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * id ������
 * @author ֣ΰ��
 * @date 2021/11/26
 */
public class Doc implements Serializable {
    private String id;
    private String creator;
    private Timestamp timestamp;
    private String description;
    private String filename;

    public Doc(String id, String creator, Timestamp timestamp, String description, String filename) {
        super();
        this.id = id;
        this.creator = creator;
        this.timestamp = timestamp;
        this.description = description;
        this.filename=filename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString(){
        return  "[id: " + id
                + ", creator: " + creator
                + ", timestamp: " + timestamp
                + ", description: " + description
                + ", filename: " + filename + "]";
    }

}