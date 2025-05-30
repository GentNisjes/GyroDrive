package be.kuleuven.gt.gyrodrive.Struct1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.gt.gyrodrive.R;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    private List<Play> PlaysList;
    public LeaderBoardAdapter(List<Play> PlaysList) {
        this.PlaysList = PlaysList;
    }
    @Override
    //binding with xml
    public LeaderBoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View orderView = layoutInflater.inflate(R.layout.play_record, parent, false);
        ViewHolder myViewHolder = new ViewHolder(orderView);
        return myViewHolder;
    }
    @Override
    //assigning values to xml objects
    public void onBindViewHolder(LeaderBoardAdapter.ViewHolder holder, int position) {
        Play playRecord = PlaysList.get(position);
        ((TextView) holder.leader_board.findViewById(R.id.username))
                .setText(playRecord.getUsername());

        //make date and time two strings and then print them
        String DT1p = playRecord.getTimestamp().substring(0, 11);
        String DT2p = playRecord.getTimestamp().substring(11);
        ((TextView) holder.leader_board.findViewById(R.id.time))
                .setText(DT2p);
        ((TextView) holder.leader_board.findViewById(R.id.date))
                .setText(DT1p);

        ((TextView) holder.leader_board.findViewById(R.id.duration))
                //needs to be set to string
                .setText(Integer.toString(playRecord.getDuration()));
        ((TextView) holder.leader_board.findViewById(R.id.device))
                .setText(playRecord.getDevice());
        ((TextView) holder.leader_board.findViewById(R.id.level))
                .setText(Integer.toString(playRecord.getLevel()));

    }
    @Override
    public int getItemCount() {
        return PlaysList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        public View leader_board;
        public ViewHolder(View LeaderBoardView) {
            super(LeaderBoardView);
            leader_board = (View) LeaderBoardView;
        }
    }
}