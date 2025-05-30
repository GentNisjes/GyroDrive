package be.kuleuven.gt.gyrodrive.Struct1;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Play implements Parcelable {
    private int id;
    private String username;

    private String timestamp;
    private int duration;

    private String device;
    private int level;
    private int user_id;

    public static final Creator<Play> CREATOR = new Creator<Play>() {
        @Override
        public Play createFromParcel(Parcel in) {
            return new Play(in);
        }
        @Override
        public Play[] newArray(int size) {
            return new Play[size];
        }
    };
    //first constructor
    public Play(int id, String username, String timestamp, int duration, String device, int level, int user_id) {
        this.id = id;
        this.username = username;
        this.timestamp = timestamp;
        this.duration = duration;
        this.device = device;
        this.level = level;
        this.user_id = user_id;
    }

    //parcel in constructor
    public Play(Parcel in) {
        this(
                in.readInt(),
                in.readString(),
                in.readString(),
                in.readInt(),
                in.readString(),
                in.readInt(),
                in.readInt()

        );
    }

    //constructor from json
    public Play(JSONObject o) {
        try {
            id = o.getInt("id_of_play");
            username = o.getString("username");
            timestamp = o.getString("timestamp");
            duration = o.getInt("duration_of_game");
            device = o.getString("device");
            level = o.getInt("level");
            user_id = o.getInt("gyrodrive_users_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    //writing to parcel
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(timestamp);
        parcel.writeInt(duration);
        parcel.writeString(device);
        parcel.writeInt(level);
        parcel.writeInt(user_id);

    }

    //writing to json
    public Map<String, String> getPostParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("id_of_play", String.valueOf(id));
        params.put("username", username);
        params.put("timestamp", timestamp);
        params.put("duration_of_game", String.valueOf(duration));
        params.put("device", device);
        params.put("level", String.valueOf(level));
        params.put("gyrodrive_users_id", String.valueOf(user_id));

        return params;

    }

    //bunch of getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public String getDevice() {
        return device;
    }

    public int getLevel() {
        return level;
    }

    public int getUser_id() {
        return user_id;
    }
}
