package com.shuzhongchen.foodordersystem.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.shuzhongchen.foodordersystem.helper.ModelUtils;
import com.shuzhongchen.foodordersystem.holders.MenuOrderViewHolder;
import com.shuzhongchen.foodordersystem.holders.ShotViewHolder;
import com.shuzhongchen.foodordersystem.models.FoodInOrder;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.models.Order;
import com.shuzhongchen.foodordersystem.models.OrderContent;
import com.shuzhongchen.foodordersystem.models.QuantityPicker;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
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

    private String MODEL_FOODLIST = "food_list";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference orderDatabase;
    FirebaseAuth mAuth;

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



        checkout = view.findViewById(R.id.order_checkout_btn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<FoodInOrder> foodList = ModelUtils.read(getContext(),
                        MODEL_FOODLIST,
                        new TypeToken<List<FoodInOrder>>(){});

                OrderContent orderContent = new OrderContent();
                List<FoodInOrder> foodInOrderList = new ArrayList<>();
                int totalPrice = 0;

                for (FoodInOrder foodInOrder : foodList) {
                    totalPrice += foodInOrder.price * foodInOrder.num;
                    foodInOrderList.add(foodInOrder);
                }

                orderContent.setOrderItems(foodInOrderList);

                Order order = new Order();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
                Date date = new Date();

                order.setOrderContent(orderContent)
                        .setOrderPlaceTime(dateFormat.format(date).toString())
                        .setPickupTime("to be set")
                        .setReadyTime("to be set")
                        .setStartTime("to be set")
                        .setStatus(String.valueOf(Order.Status.queued))
                        .setTotalPrice(totalPrice);

                order.setUid(mAuth.getCurrentUser().getUid());

                Long tsLong = System.currentTimeMillis() / 1000;
                String uniqueID = tsLong.toString();

                orderDatabase.child(uniqueID)
                        .setValue(order)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "order placed!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        loadAllOrder();

        return view;
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
        orderDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Order order = postSnapshot.getValue(Order.class);
                    System.out.println("Shuzhong debug order: " + order.toString() + "\n");
                    System.out.println("Shuzhong debug order start time: " + order.getStartTime() + "\n");
                    System.out.println("Shuzhong debug order total time: " + order.getTotalPrice() + "\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: " ,firebaseError.getMessage());
            }
        });
    }


}
