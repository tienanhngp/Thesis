package thesis.readsensorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private Context mContext;
    private List<Sensor> mListSensor;

    public SensorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Sensor> List){
        this.mListSensor = List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor,parent,false);

        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  SensorAdapter.SensorViewHolder holder, int position) {
        Sensor sensor = mListSensor.get(position);
        if(sensor == null){
            return;
        }

        holder.imgSen.setImageResource(sensor.getResouceImage());
        holder.tvSenName.setText(sensor.getSensorName());
        holder.tvSenVal.setText(sensor.getSensorValue());

    }

    @Override
    public int getItemCount() {
        if(mListSensor != null)
            return mListSensor.size();
        return 0;
    }

    public class SensorViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgSen;
        private TextView tvSenName;
        private TextView tvSenVal;

        public SensorViewHolder(@NonNull  View itemView) {
            super(itemView);

            imgSen = itemView.findViewById(R.id.imgSen);
            tvSenName = itemView.findViewById(R.id.tvSenName);
            tvSenVal = itemView.findViewById(R.id.tvSenVal);
        }
    }
}
