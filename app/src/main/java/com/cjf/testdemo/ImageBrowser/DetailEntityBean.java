package com.cjf.testdemo.ImageBrowser;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sohu-mac on 16/11/28.
 */

public class DetailEntityBean implements Parcelable {

//    @Id(autoincrement = true)
    private Long _id;

    private int read_progress = 0;

    /**
     * article_id : 6316941574689261000
     * channels : [{"id":1,"name":"频道一"},{"id":2,"name":"频道二"}]
     * content :
     * description : 描述
     * images : [{"note":"图片注释","url":"http://p3.pstatp.com/large/bdc0005b63b75cc3f42"},{"note":"图片注释","url":"http://p1.pstatp.com/large/ba60006c418058f9f80"}]
     * keywords : ["关键词一","关键词二","关键词三"]
     * media : {"authors":["作者名"],"avatar_url":"http://p1.pstatp.com/large/11394/7515481817","id":5859652218,"name":"哈哈竞"}
     * monitor : []
     * publish_time : 1472120912
     * source : 1
     * subtitle : 副标题
     * title : 哈哈标题
     * topic : ["主题一","主题二","主题三"]
     * type : 1
     * videos : [{"actors":["演员一","演员二"],"comment":"评论","duration":2000,"hd_id":23423,"type":1,"url":"http://p3.pstatp.com/large/bdc0005b63b75cc3f42"},{"actors":["演员一","演员二"],"comment":"评论","duration":2000,"hd_id":23423,"type":1,"url":"http://p3.pstatp.com/large/bdc0005b63b75cc3f42"}]
     * error_code: 0,
     * message : "success"
     */

//    @Unique
    private long article_id;
    private String content;
    private String description;
    /**
     * authors : ["作者名"]
     * avatar_url : http://p1.pstatp.com/large/11394/7515481817
     * id : 5859652218
     * name : 哈哈竞
     */

//    @Convert(converter = MediaConvertor.class, columnType = String.class)
    private MediaBean media;
    private int publish_time;
    private int source;
    private String subtitle;
    private String title;
    private int type;
    /**
     * id : 1
     * name : 频道一
     */
//    @Convert(converter = ChannelConvertor.class, columnType = String.class)
    private List<ChannelsBean> channels;
    /**
     * note : 图片注释
     * url : http://p3.pstatp.com/large/bdc0005b63b75cc3f42
     */
//    @Convert(converter = ImageConvertor.class, columnType = String.class)
    private List<ImagesBean> images;
//    @Convert(converter = StringConvertor.class, columnType = String.class)
    private List<String> keywords;
//    @Convert(converter = StringConvertor.class, columnType = String.class)
    private List<String> monitor;
//    @Convert(converter = StringConvertor.class, columnType = String.class)
    private List<String> topic;
    /**
     * actors : ["演员一","演员二"]
     * comment : 评论
     * duration : 2000
     * hd_id : 23423
     * type : 1
     * url : http://p3.pstatp.com/large/bdc0005b63b75cc3f42
     */
//    @Convert(converter = VideoConvertor.class, columnType = String.class)
    private List<VideosBean> videos;
    private int error_code;
    private String message;

    public int getRead_progress() {
        return read_progress;
    }

    public void setRead_progress(int read_progress) {
        this.read_progress = read_progress;
    }

    public long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(long article_id) {
        this.article_id = article_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }

    public int getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(int publish_time) {
        this.publish_time = publish_time;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ChannelsBean> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelsBean> channels) {
        this.channels = channels;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getMonitor() {
        return monitor;
    }

    public void setMonitor(List<String> monitor) {
        this.monitor = monitor;
    }

    public List<String> getTopic() {
        return topic;
    }

    public void setTopic(List<String> topic) {
        this.topic = topic;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class MediaBean implements Parcelable {
        private String avatar_url;
        private long id;
        private String name;
        private List<String> authors;

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getAuthors() {
            return authors;
        }

        public void setAuthors(List<String> authors) {
            this.authors = authors;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.avatar_url);
            dest.writeLong(this.id);
            dest.writeString(this.name);
            dest.writeStringList(this.authors);
        }

        public MediaBean() {
        }

        protected MediaBean(Parcel in) {
            this.avatar_url = in.readString();
            this.id = in.readLong();
            this.name = in.readString();
            this.authors = in.createStringArrayList();
        }

        public static final Creator<MediaBean> CREATOR = new Creator<MediaBean>() {
            @Override
            public MediaBean createFromParcel(Parcel source) {
                return new MediaBean(source);
            }

            @Override
            public MediaBean[] newArray(int size) {
                return new MediaBean[size];
            }
        };
    }

    public static class ChannelsBean implements Parcelable {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
        }

        public ChannelsBean() {
        }

        protected ChannelsBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
        }

        public static final Creator<ChannelsBean> CREATOR = new Creator<ChannelsBean>() {
            @Override
            public ChannelsBean createFromParcel(Parcel source) {
                return new ChannelsBean(source);
            }

            @Override
            public ChannelsBean[] newArray(int size) {
                return new ChannelsBean[size];
            }
        };
    }

    public static class ImagesBean implements Parcelable {
        private String note;
        private String url;
        private int height;
        private int width;
        private String type;


        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.note);
            dest.writeString(this.url);
            dest.writeInt(this.height);
            dest.writeInt(this.width);
            dest.writeString(this.type);
        }

        public ImagesBean() {
        }

        protected ImagesBean(Parcel in) {
            this.note = in.readString();
            this.url = in.readString();
            this.height = in.readInt();
            this.width = in.readInt();
            this.type = in.readString();
        }

        public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
            @Override
            public ImagesBean createFromParcel(Parcel source) {
                return new ImagesBean(source);
            }

            @Override
            public ImagesBean[] newArray(int size) {
                return new ImagesBean[size];
            }
        };
    }

    public static class VideosBean implements Parcelable {

        private String comment;
        private int duration;
        private int hd_id;
        private int type;
        private String url;
        private List<String> actors;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getHd_id() {
            return hd_id;
        }

        public void setHd_id(int hd_id) {
            this.hd_id = hd_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getActors() {
            return actors;
        }

        public void setActors(List<String> actors) {
            this.actors = actors;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.comment);
            dest.writeInt(this.duration);
            dest.writeInt(this.hd_id);
            dest.writeInt(this.type);
            dest.writeString(this.url);
            dest.writeStringList(this.actors);
        }

        public VideosBean() {
        }

        protected VideosBean(Parcel in) {
            this.comment = in.readString();
            this.duration = in.readInt();
            this.hd_id = in.readInt();
            this.type = in.readInt();
            this.url = in.readString();
            this.actors = in.createStringArrayList();
        }

        public static final Creator<VideosBean> CREATOR = new Creator<VideosBean>() {
            @Override
            public VideosBean createFromParcel(Parcel source) {
                return new VideosBean(source);
            }

            @Override
            public VideosBean[] newArray(int size) {
                return new VideosBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.read_progress);
        dest.writeLong(this.article_id);
        dest.writeString(this.content);
        dest.writeString(this.description);
        dest.writeParcelable(this.media, flags);
        dest.writeInt(this.publish_time);
        dest.writeInt(this.source);
        dest.writeString(this.subtitle);
        dest.writeString(this.title);
        dest.writeInt(this.type);
        dest.writeTypedList(this.channels);
        dest.writeTypedList(this.images);
        dest.writeStringList(this.keywords);
        dest.writeStringList(this.monitor);
        dest.writeStringList(this.topic);
        dest.writeTypedList(this.videos);
        dest.writeInt(this.error_code);
        dest.writeString(this.message);
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public DetailEntityBean() {
    }

    protected DetailEntityBean(Parcel in) {
        this.read_progress = in.readInt();
        this.article_id = in.readLong();
        this.content = in.readString();
        this.description = in.readString();
        this.media = in.readParcelable(MediaBean.class.getClassLoader());
        this.publish_time = in.readInt();
        this.source = in.readInt();
        this.subtitle = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
        this.channels = in.createTypedArrayList(ChannelsBean.CREATOR);
        this.images = in.createTypedArrayList(ImagesBean.CREATOR);
        this.keywords = in.createStringArrayList();
        this.monitor = in.createStringArrayList();
        this.topic = in.createStringArrayList();
        this.videos = in.createTypedArrayList(VideosBean.CREATOR);
        this.error_code = in.readInt();
        this.message = in.readString();
    }

    public DetailEntityBean(Long _id, int read_progress, long article_id, String content, String description, MediaBean media, int publish_time, int source, String subtitle, String title, int type, List<ChannelsBean> channels, List<ImagesBean> images, List<String> keywords,
                            List<String> monitor, List<String> topic, List<VideosBean> videos, int error_code, String message) {
        this._id = _id;
        this.read_progress = read_progress;
        this.article_id = article_id;
        this.content = content;
        this.description = description;
        this.media = media;
        this.publish_time = publish_time;
        this.source = source;
        this.subtitle = subtitle;
        this.title = title;
        this.type = type;
        this.channels = channels;
        this.images = images;
        this.keywords = keywords;
        this.monitor = monitor;
        this.topic = topic;
        this.videos = videos;
        this.error_code = error_code;
        this.message = message;
    }

    public static final Parcelable.Creator<DetailEntityBean> CREATOR = new Parcelable.Creator<DetailEntityBean>() {
        @Override
        public DetailEntityBean createFromParcel(Parcel source) {
            return new DetailEntityBean(source);
        }

        @Override
        public DetailEntityBean[] newArray(int size) {
            return new DetailEntityBean[size];
        }
    };
}
