package thesis.readsensorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    String temVal, soilMosVal, airQualityVal, lightVal;
    Button btnLogOut;
    TextView tvUserEmail;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnLogOut = findViewById(R.id.btnLogOut);
        tvUserEmail = findViewById(R.id.tvUserEmail);

        RecyclerView rcvSensor = findViewById(R.id.rcvSensor);
        SensorAdapter mSensorAdapter = new SensorAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) tvUserEmail.setText(mAuth.getCurrentUser().getEmail());

        btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        });
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<Sensor> list = new ArrayList<>();




                temVal = datasnapshot.child("Temp").getValue().toString();
                list.add(new Sensor(R.drawable.tem, "Nhiệt độ", temVal + "°C"));
                lightVal = datasnapshot.child("Light").getValue().toString();
                if(lightVal.equals("1")){
                    list.add(new Sensor(R.drawable.light, "Ánh sáng", "Đủ sáng"));
                }
                if(lightVal.equals("2")){
                    list.add(new Sensor(R.drawable.light, "Ánh sáng", "Cảnh báo"));
                }
                if(lightVal.equals("3")){
                    list.add(new Sensor(R.drawable.light, "Ánh sáng", "Thiếu sáng"));
                }
                airQualityVal = datasnapshot.child("airQuality").getValue().toString();
                if(airQualityVal.equals("1")){
                    list.add(new Sensor(R.drawable.air, "Chất lượng khí","Bình thường"));
                }
                if(airQualityVal.equals("2")){
                    list.add(new Sensor(R.drawable.air, "Chất lượng khí", "Cảnh báo"));
                }
                if(airQualityVal.equals("3")){
                    list.add(new Sensor(R.drawable.air, "Chất lượng khí", "Nguy hiểm"));
                }
                soilMosVal = datasnapshot.child("SoilMos").getValue().toString();
                list.add(new Sensor(R.drawable.humid1, "Độ ẩm đất", soilMosVal + "%"));

                mSensorAdapter.setData(list);
                rcvSensor.setLayoutManager(gridLayoutManager);
                rcvSensor.setAdapter(mSensorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }



}

