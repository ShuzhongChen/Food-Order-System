package com.shuzhongchen.foodordersystem.view.base;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.adapter.MenuOrderAdapter;
import com.shuzhongchen.foodordersystem.configuration.SendMail;
import com.shuzhongchen.foodordersystem.helper.ModelUtils;
import com.shuzhongchen.foodordersystem.helper.RangeTimePickerDialog;
import com.shuzhongchen.foodordersystem.holders.MenuOrderViewHolder;
import com.shuzhongchen.foodordersystem.holders.ShotViewHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.models.Order;
import com.shuzhongchen.foodordersystem.models.OrderContent;
import com.shuzhongchen.foodordersystem.models.QuantityPicker;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by shuzhongchen on 5/8/18.
 */

public class CheckOutFragment extends Fragment {
    @BindView(R.id.order_recycler_view)
    RecyclerView recyclerView;

    Button checkout;
    Button cancel;
    Button pickdate;
    Button picktime;
    DatePickerDialog datePickerDialog;
    RangeTimePickerDialog timePickerDialog;
    TextView tot;

    ArrayList<String> startTimes = new ArrayList<>();
    ArrayList<String> readyTimes = new ArrayList<>();

    private String MODEL_FOODLIST = "food_list";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference orderDatabase;
    FirebaseAuth mAuth;

    private String OrderPickupTime;

    public static CheckOutFragment newInstance() {
        Bundle args = new Bundle();
        CheckOutFragment fragment = new CheckOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_recycler_view, container, false);
        ButterKnife.bind(this, view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        orderDatabase = firebaseDatabase.getReference("Orders");

        mAuth = FirebaseAuth.getInstance();

        List<FoodInOrder> foodListForTot = ModelUtils.read(getContext(),
                MODEL_FOODLIST,
                new TypeToken<List<FoodInOrder>>(){});
        int totalPrice = 0;
        for (FoodInOrder foodInOrder : foodListForTot) {
            totalPrice += foodInOrder.getPrice() * foodInOrder.getNum();
        }
        tot = view.findViewById(R.id.txtTotalPrice);
        tot.setText("$" + totalPrice);

        cancel = view.findViewById(R.id.order_cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<FoodInOrder> newList = new ArrayList<FoodInOrder>();
                ModelUtils.save(getContext(), MODEL_FOODLIST, newList);
                Fragment newFragment = new BaseFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });


        checkout = view.findViewById(R.id.order_checkout_btn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pickupDate = pickdate.getText().toString();
                String pickupTime = picktime.getText().toString();

                String[] str = pickupTime.split(":");

                StringBuilder sb = new StringBuilder();
                sb.append(pickupDate);

                for (String s : str) {
                    sb.append("/");
                    sb.append(s);
                }

                System.out.println("pickup date: " + pickupDate + "\n");
                System.out.println("pickup time: " + pickupTime + "\n");

                List<FoodInOrder> foodList = ModelUtils.read(getContext(),
                        MODEL_FOODLIST,
                        new TypeToken<List<FoodInOrder>>(){});

                if (foodList == null || foodList.size() == 0) {
                    Toast.makeText(getContext(), "Please add some items to cart!", Toast.LENGTH_SHORT).show();
                    return;
                }

                OrderContent orderContent = new OrderContent();
                List<FoodInOrder> foodInOrderList = new ArrayList<>();
                int totalPrice = 0;
                int totalPrepTime = 0;

                for (FoodInOrder foodInOrder : foodList) {
                    totalPrepTime += foodInOrder.getPreptime() * foodInOrder.getNum();
                    totalPrice += foodInOrder.getPrice() * foodInOrder.getNum();
                    foodInOrderList.add(foodInOrder);
                }

                String startTime = calculateStartTime(pickupDate, pickupTime, totalPrepTime);

                orderContent.setOrderItems(foodInOrderList);

                Order order = new Order();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
                Date date = new Date();


                String readyTime = sb.toString();

                String[] newRTarray3 = startTime.split("/");
                if(Integer.parseInt(newRTarray3[3]) < 5) {
                    Toast.makeText(getContext(), "Sorry, It's too early! Select an latter time slot.", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean conflict = false;
                for (int i = 0; i < startTimes.size(); i++) {
                    if (isConflict(startTime, readyTime, startTimes.get(i), readyTimes.get(i))) {
                        conflict = true;
                        break;
                    }
                }

                if (conflict) {
                    for (int j = 6; j <= 20; j++) {
                        boolean tmp = false;
                        String newStartTime = calculateStartTime(pickupDate, j+":00", totalPrepTime);
                        String[] newRTarray = readyTime.split("/");
                        String newReadyTime = "";

                        newRTarray[3] = j + "";
                        newRTarray[4] = "00";

                        for (int i = 0; i < newRTarray.length; i++) {
                            newReadyTime = newReadyTime + newRTarray[i] + "/";
                        }

                        newReadyTime = newReadyTime.substring(0, newReadyTime.length() - 1);


                        for (int i = 0; i < startTimes.size(); i++) {
                            if (isConflict(newStartTime, newReadyTime, startTimes.get(i), readyTimes.get(i))) {
                                tmp = true;
                                break;
                            }
                        }

                        if (!tmp) {

                            String[] newRTarray2 = newReadyTime.split("/");
                            String newReadyTime2 = newRTarray2[3] + ":" + newRTarray2[4];


                            Toast.makeText(getContext(), "Sorry, we are too busy at this time! Select " + newReadyTime2, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    Toast.makeText(getContext(), "Sorry, we are too busy at this time! Select another time slot.", Toast.LENGTH_SHORT).show();
                    return;
                }

                order.setOrderContent(orderContent)
                        .setOrderPlaceTime(dateFormat.format(date).toString())
                        .setPickupTime(sb.toString())
                        .setReadyTime(sb.toString())
                        .setStartTime(startTime)
                        .setStatus(String.valueOf(Order.Status.queued))
                        .setTotalPrice(totalPrice);

                order.setUid(mAuth.getCurrentUser().getUid());

                try {
                    sendConfirmationEmail(getContext(), mAuth.getCurrentUser().getEmail(), sb.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long tsLong = System.currentTimeMillis() / 1000;
                String uniqueID = tsLong.toString();

                orderDatabase.child(uniqueID)
                        .setValue(order)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "order placed!", Toast.LENGTH_SHORT).show();
                                List<FoodInOrder> newList = new ArrayList<FoodInOrder>();
                                ModelUtils.save(getContext(), MODEL_FOODLIST, newList);

                                Fragment newFragment = new OrderHistoryFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                transaction.replace(R.id.fragment_container, newFragment);
                                transaction.addToBackStack(null);

                                transaction.commit();
                            }
                        });




            }
        });

        pickdate = view.findViewById(R.id.selectDate);
        pickdate.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                pickdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_WEEK, +7);
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());

                datePickerDialog.show();
            }
        });

        picktime = view.findViewById(R.id.selectTime);
        picktime.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                //int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                //int minute = mcurrentTime.get(Calendar.MINUTE);
                int hour = 12;
                int minute = 0;
                // spinner mode, build in theme: android.R.style.Theme_Holo_Light_Dialog
                timePickerDialog = new RangeTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        picktime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time

                timePickerDialog.setMin(6,0);
                timePickerDialog.setMax(21,0);
                timePickerDialog.show();
            }
        });

        loadAllOrder();


        return view;
    }

    private void sendConfirmationEmail(Context context, String email, String s) throws ParseException {
        Log.d("Email confirmation", "sendConfirmationEmail: " + email);

        String subject = "IFood Order Confirmation";
        String message = "Your order will be ready at ";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
        Date date = sdf.parse(s);
        Log.d("date", "sendConfirmationEmail: " + date.toString());
        message += date.toString();
        Log.d("message", "sendConfirmationEmail: " + message);
        SendMail sm = new SendMail(context, email, subject, message);
        sm.execute();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        List<FoodInOrder> foodList = ModelUtils.read(getContext(),
                MODEL_FOODLIST,
                new TypeToken<List<FoodInOrder>>(){});
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MenuOrderAdapter(foodList));

    }


    private void loadAllOrder() {
        startTimes = new ArrayList<>();
        readyTimes = new ArrayList<>();

        orderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);

                    if (order.getStatus().equals("queued")) {
                        startTimes.add(order.getStartTime());
                        readyTimes.add(order.getReadyTime());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: " ,firebaseError.getMessage());
            }
        });
    }


    private String calculateStartTime(String pickupDate, String pickuptime, int totalPrepTime) {
        int minute = totalPrepTime % 60;

        String[] str = pickuptime.split(":");
        int currentMin = Integer.parseInt(str[1]);
        int currentHour = Integer.parseInt(str[0]);

        currentMin = currentMin >= minute ? currentMin - minute : currentMin + 60 - minute;
        currentHour = currentMin >= minute ? currentHour : currentHour - 1;

        StringBuilder sb = new StringBuilder();
        return sb.append(pickupDate).append("/").append(String.valueOf(currentHour))
                .append("/").append(String.valueOf(currentMin)).toString();

    }

    private boolean isConflict(String s1, String r1, String s2, String r2) {
        String[] s1split = s1.split("/");
        String[] r1split = r1.split("/");
        String[] s2split = s2.split("/");
        String[] r2split = r2.split("/");

        for (int i = 0; i < 3; i++) {
            if (!s1split[i].equals(s2split[i])){
                return false;
            }
        }


        int ns1 = Integer.parseInt(s1split[3]) * 60 + Integer.parseInt(s1split[4]);
        int nr1 = Integer.parseInt(r1split[3]) * 60 + Integer.parseInt(s1split[4]);
        int ns2 = Integer.parseInt(s2split[3]) * 60 + Integer.parseInt(s1split[4]);
        int nr2 = Integer.parseInt(r2split[3]) * 60 + Integer.parseInt(s1split[4]);

        if (nr1 < ns2 || nr2 < ns1){
            return false;
        }


        return true;
    }

}
