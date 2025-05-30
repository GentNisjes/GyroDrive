package be.kuleuven.gt.gyrodrive.Struct1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Player implements Parcelable {

    private String user, firstName, lastName;
    private int time;

    public Player(String u, int t){
        user = u;
        time = t;
    }

    public Player(Parcel in) {
        this(
                in.readString(),
                in.readInt()
        );
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getUser() {
        return user;
    }


    public int getTime() {
        return time;
    }


    public String getDescription() {
        return user + "took " + time + "s";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(user);
        parcel.writeInt(time);
    }

    @Override
    public String toString(){
        return "Player{username=" + user + ", duration_of_game=" + time;
    }

    public Map<String, String> getPostParameters() {
        Map<String, String> params = new HashMap<>();
        params.put("username", user);
        params.put("duration", String.valueOf(time));
        params.put("device", "SamsungA");
        params.put("level", "1");
        params.put("id", "1");

        return params;
    }

    public Player(JSONObject o) {
        try {
            user = o.getString("username");
            time = o.getInt("duration_of_game");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

