package com.demo.smokingcontrolapp.activities.target;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.demo.smokingcontrolapp.R;
import com.demo.smokingcontrolapp.models.UserInfoApp;
import com.demo.smokingcontrolapp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TargetActivity extends AppCompatActivity {

    private Toolbar toolbarTarget;
    private Spinner spinner;
    private final List<String> targetNames = Arrays.asList("-- Choose your target --", "1 Week", "2 Week", "3 Week", "4 Week");
    private TextView txvStartDate, txvEndDate;
    private final TargetViewModel viewModel = new TargetViewModel(this.getApplication());
    private Button btnAdd;
    private long endDate;
    private String targetName;
    private EditText edtCountOfCigarette;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date currentDate;
    private Utils utils = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        init();

        toolbarTarget.setNavigationOnClickListener(v -> finish());

        // set current time for txvStartDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txvStartDate.setText(dateFormat.format(System.currentTimeMillis()));

        //event click
        event();
    }

    private void event() {

        // even click add new target
        btnAdd.setOnClickListener(v -> {
            String count = edtCountOfCigarette.getText().toString().trim();
            if (!count.equals("") && endDate != 0) {
                UserInfoApp userInfoApp = utils.getUserInfo(this);
                viewModel.addNewTarget(userInfoApp.getuID(), targetName, System.currentTimeMillis(), endDate, Integer.parseInt(count),
                        bool -> {
                            if (bool) {
                                edtCountOfCigarette.setText("");
                                finish();
                            } else {
                                Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                if (!count.equals("")) {
                    Toast.makeText(this, "please choose your target", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "please input target", Toast.LENGTH_SHORT).show();
            }
        });

        // even click spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                targetName = String.valueOf(adapter.getItem(position));
                //current date
                currentDate = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);

                switch (targetName) {
                    case "1 Week": {
                        c.add(Calendar.DATE, 7);
                        Date currentTime = c.getTime();
                        txvEndDate.setText(dateFormat.format(currentTime));
                        endDate = currentTime.getTime();
                        break;
                    }
                    case "2 Week": {
                        c.add(Calendar.WEEK_OF_MONTH, 2);
                        Date currentTime = c.getTime();
                        txvEndDate.setText(dateFormat.format(currentTime));
                        endDate = currentTime.getTime();
                        break;
                    }
                    case "3 Week": {
                        c.add(Calendar.WEEK_OF_MONTH, 3);
                        Date currentTime = c.getTime();
                        txvEndDate.setText(dateFormat.format(currentTime));
                        endDate = currentTime.getTime();
                        break;
                    }
                    case "4 Week": {
                        c.add(Calendar.MONTH, 1);
                        Date currentTime = c.getTime();
                        txvEndDate.setText(dateFormat.format(currentTime));
                        endDate = currentTime.getTime();
                        break;
                    }
                    default:
                        endDate = 0;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void init() {

        //toolbar
        toolbarTarget = findViewById(R.id.tb_add_new_target);
        toolbarTarget.setTitle(getResources().getString(R.string.them_muc_tieu));
        toolbarTarget.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarTarget.setNavigationIcon(R.drawable.ic_baseline_west_24);
        setSupportActionBar(toolbarTarget);

        //spinner
        spinner = findViewById(R.id.spinner_choose_target);
        ArrayAdapter<String> targetAdapter = new ArrayAdapter<>(
                this,
                R.layout.item_spinner,
                targetNames);

        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(targetAdapter);

        // textView
        txvEndDate = findViewById(R.id.txv_end);
        txvStartDate = findViewById(R.id.txv_start);

        //Button
        btnAdd = findViewById(R.id.btn_add_target);

        //EditText
        edtCountOfCigarette = findViewById(R.id.edit_number_cigarette);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}