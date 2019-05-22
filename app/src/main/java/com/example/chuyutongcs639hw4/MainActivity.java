package com.example.chuyutongcs639hw4;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView circleRecyclerView;
    CircleRecyclerViewAdapter mAdapter;
    Button addButton;
    EditText radiusInput;
    EditText speedInput;
    ImageView selectedColorView;
    int selectedColor;
    ArrayList<Circle> circleList;
    int currentPosition;
    View createCreationInterfaceView;
    View speedControlInterfaceView;
    Button minusButton;
    Button plusButton;

    TextView currentSpeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureRecyclerView();

        radiusInput = findViewById(R.id.radiusEditText);
        speedInput= findViewById(R.id.speedEditText);
        addButton = findViewById(R.id.addButton);
        createCreationInterfaceView = findViewById(R.id.createCreationInterface);
        speedControlInterfaceView = findViewById(R.id.speedControlInterface);
        currentSpeedView = findViewById(R.id.currentSpeed);
        minusButton = findViewById(R.id.minusButton);
        plusButton = findViewById(R.id.plusButton);
        selectedColorView = null;

        setAddTextChangedListener();
        addColorClickListeners();
        setAddButtonOnClickListener();



        mAdapter.setOnItemClickListener(new CircleRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mAdapter.setSelectedPosition(position);
                currentPosition = mAdapter.getSelectedPosition();
                if(currentPosition != Adapter.NO_SELECTION){
                    createCreationInterfaceView.setVisibility(View.INVISIBLE);
                    speedControlInterfaceView.setVisibility(View.VISIBLE);
                    Circle currentCircle = mAdapter.getItem(mAdapter.getSelectedPosition());
                    currentSpeedView.setText(Integer.toString(currentCircle.mSpeed));
                    plusButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentCircle.mSpeed +=1;
                            currentSpeedView.setText(Integer.toString(currentCircle.mSpeed));
                            mAdapter.notifyDataSetChanged();
                        }
                    });

                    minusButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(currentCircle.mSpeed > 1) {
                                currentCircle.mSpeed -= 1;
                                currentSpeedView.setText(Integer.toString(currentCircle.mSpeed));
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }
                else{
                    createCreationInterfaceView.setVisibility(View.VISIBLE);
                    speedControlInterfaceView.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    public void configureRecyclerView() {
        circleList = new ArrayList<>();
//        circleList.add(new Circle(Color.BLUE, 40, 40));
//        circleList.add(new Circle(Color.RED, 20, 15));

        circleRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new CircleRecyclerViewAdapter(circleList);
        circleRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        circleRecyclerView.setHasFixedSize(true);
        circleRecyclerView.setAdapter(mAdapter);

        circleRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder viewHolder) {
                CircleRecyclerViewAdapter.RecyclerViewHolder rvh = (CircleRecyclerViewAdapter.RecyclerViewHolder)viewHolder;
                rvh.mCircleView.stopCircle();
            }
        });

    }

    private void addColorClickListeners() {

        View.OnClickListener colorListener = view -> {

            if(selectedColorView != null)
                selectedColorView.setBackgroundColor(Color.WHITE);

            selectedColorView = selectedColorView == view ? null : (ImageView)view;

            if(selectedColorView != null)
                selectedColorView.setBackgroundColor(Color.BLACK);


            int selectedImageId = selectedColorView == null ? 0 : selectedColorView.getId();
            switch (selectedImageId) {
                case R.id.color1:
                    selectedColor = Color.parseColor("#f08890");
                    break;
                case R.id.color2:
                    selectedColor = Color.parseColor("#0f0876");
                    break;
                case R.id.color3:
                    selectedColor = Color.parseColor("#058904");
                    break;
                case R.id.color4:
                    selectedColor = Color.parseColor("#0589f4");
                    break;
                case R.id.color5:
                    selectedColor = Color.parseColor("#f0893f");
                    break;
            }


        };

        //add above listener to colors
        findViewById(R.id.color1).setOnClickListener(colorListener);
        findViewById(R.id.color2).setOnClickListener(colorListener);
        findViewById(R.id.color3).setOnClickListener(colorListener);
        findViewById(R.id.color4).setOnClickListener(colorListener);
        findViewById(R.id.color5).setOnClickListener(colorListener);
    }

    private void setAddTextChangedListener(){
        radiusInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable edt) {
                if (edt.length() == 1 && edt.toString().equals("0"))
                   radiusInput.setText("");
            }
        });

        speedInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable edt) {
                if (edt.length() == 1 && edt.toString().equals("0"))
                    speedInput.setText("");
            }
        });
    }

    public void setAddButtonOnClickListener(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areInputsFilled()) return;

                int radiusInt;
                try {
                    //try to parse student count from the text field
                    radiusInt = Integer.parseInt(radiusInput.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return;
                }

                int speedInt;
                try {
                    //try to parse student count from the text field
                    speedInt = Integer.parseInt(speedInput.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return;
                }

                if(radiusInt > 40) {
                    Toast.makeText(MainActivity.this, R.string.radius_cannot_be_greater_than_40, Toast.LENGTH_LONG).show();
                    radiusInput.setText("");
                }
                else {
                    circleList.add(new Circle(selectedColor, radiusInt, speedInt));
                    selectedColorView.setBackgroundColor(Color.WHITE);
                    selectedColorView = null;
                    selectedColor = 0;
                    radiusInput.setText("");
                    speedInput.setText("");
                }


            }
        });
    }

    private boolean areInputsFilled() {
        if (radiusInput.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.radius_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (speedInput.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.speed_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            return false;
        }else if(selectedColorView == null){
            Toast.makeText(this, R.string.please_select_a_color,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
