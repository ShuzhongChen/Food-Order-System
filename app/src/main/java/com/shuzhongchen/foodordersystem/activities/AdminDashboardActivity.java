package com.shuzhongchen.foodordersystem.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shuzhongchen.foodordersystem.helper.MyRecyclerItemClickListener;
import com.shuzhongchen.foodordersystem.R;
import com.shuzhongchen.foodordersystem.holders.MenuViewHolder;
import com.shuzhongchen.foodordersystem.models.Menu;
import com.shuzhongchen.foodordersystem.fragments.MenuSortFragment;
import com.shuzhongchen.foodordersystem.models.Order;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by jinchengcheng on 4/27/18.
 */

public class AdminDashboardActivity extends AppCompatActivity {

    private Activity content;

    private FloatingActionButton floatingActionButton;
    private final int requestCode = 1;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference menuDatabase;
    DatabaseReference orderDB;

    StorageReference storageRef;

    static int id;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Menu, MenuViewHolder> adapter;

    private Bitmap bitmap;
    private ImageButton imageButton;

    private int maxKey;

    private DrawerLayout mDrawerLayout;

    private List<String> keyList;
    private ArrayList<Menu> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_fab);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_menu);
        layoutManager = new LinearLayoutManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.admin_drawer_layout);

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        NavigationView navigationView = findViewById(R.id.admin_nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        switch (item.getItemId()) {
                            case R.id.status_report:

                                System.out.println("status report");

                                Intent i = new Intent(AdminDashboardActivity.this,MenuSortActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("MENU_LIST", menuList);
                                i.putExtras(bundle);
                                startActivity(i);

                                setTitle("Order Status Report");
                                break;
                            case R.id.popularity_report:
                                System.out.println("status report");
                                break;
                            case R.id.reset_order:

                                final AlertDialog.Builder alertDialog =new AlertDialog.Builder(AdminDashboardActivity.this);
                                alertDialog.setTitle("Are you want to delete all orders?");
                                alertDialog.setCancelable(false);
                                alertDialog.setMessage("By deleting this, item will permanently be deleted. Are you still want to delete this?");
                                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });

                                alertDialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.out.println("remove clicked: " + "\n");
                                        removeAllOrders();
                                        dialog.dismiss();
                                    }

                                });

                                alertDialog.show();
                                System.out.println("reset order");
                                break;
                            case R.id.admin_log_out:
                                Intent goMainActivity = new Intent(AdminDashboardActivity.this, MainActivity.class);
                                startActivity(goMainActivity);
                                finish();
                                break;

                        }
                        mDrawerLayout.closeDrawers();

                        return false;
                    }
                }
        );

        firebaseDatabase = FirebaseDatabase.getInstance();
        menuDatabase = firebaseDatabase.getReference("menu");

        orderDB = firebaseDatabase.getReference("Orders");

        keyList = new ArrayList<>();
        menuList = new ArrayList<>();
        orderDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                  keyList.add(postSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: " ,firebaseError.getMessage());
            }
        });

        menuDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Menu menu = postSnapshot.getValue(Menu.class);
                    menuList.add(menu);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: " ,firebaseError.getMessage());
            }
        });



        // Create a storage reference from our app
        storageRef = FirebaseStorage.getInstance().getReference();
        maxKey = 0;

        loadAllMenu();

        floatingActionButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showCreateMenuLayout();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode == RESULT_OK){
            this.bitmap = (Bitmap)data.getExtras().get("data");
            imageButton.setImageBitmap(bitmap);
            imageButton.setScaleType(ImageView.ScaleType.FIT_XY);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeAllOrders() {
        for (String key : keyList) {
           orderDB.child(key)
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminDashboardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            adapter.notifyDataSetChanged();
        }
        Toast.makeText(AdminDashboardActivity.this, "Orders Remove Succeed!", Toast.LENGTH_SHORT).show();
    }

    private void loadAllMenu() {

        FirebaseRecyclerOptions<Menu> allMenu = new FirebaseRecyclerOptions.Builder<Menu>()
                .setQuery(menuDatabase, Menu.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(allMenu) {
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemview = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.menu_dispaly, parent, false);
                return new MenuViewHolder(itemview);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, final int position, @NonNull final Menu model) {

                holder.idTV.setText("" + adapter.getRef(position).getKey());
                holder.nameTV.setText(model.getName());
                holder.categoryTV.setText(model.getCategory());
                holder.caloriesTV.setText("" + model.getCalories());
                holder.UnitPriceTV.setText("" + model.getUnitprice());
                holder.PrepTimeTV.setText("" + model.getPreptime());

                maxKey = Math.max(maxKey, Integer.parseInt(adapter.getRef(position).getKey()));

                Picasso.get().load(model.getImage())
                        .into(holder.imageButton);


            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new MyRecyclerItemClickListener(getApplicationContext(),recyclerView,new MyRecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                System.out.println("on item clicked: " + "\n");
            }

            @Override
            public void onLongClick(View view, final int position) {

                System.out.println("position: " + position + "\n");
                final AlertDialog.Builder alertDialog =new AlertDialog.Builder(AdminDashboardActivity.this);
                alertDialog.setTitle("Are you want to delete this");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("By deleting this, item will permanently be deleted. Are you still want to delete this?");
                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                alertDialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("remove clicked: " + "\n");
                        removeMenu(adapter.getRef(position).getKey());
                        dialog.dismiss();
                    }

                });

                alertDialog.show();
            }
        }));

    }



    private void removeMenu(String key) {
        menuDatabase.child(key)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminDashboardActivity.this, "Remove Succeed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminDashboardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();
    }


    private void showCreateMenuLayout() {

        AlertDialog.Builder create_menu_dialog = new AlertDialog.Builder(AdminDashboardActivity.this);
        create_menu_dialog.setTitle("Create Menu");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        final View CreateView = layoutInflater.inflate(R.layout.activity_menu_detail,null);

        String[] items = new String[]{"--- choose ---", "drink", "appetizer", "main course", "dessert"};
        final Spinner categorySpinner = (Spinner)CreateView.findViewById(R.id.categorySpinner);
        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(AdminDashboardActivity.this, android.R.layout.simple_spinner_item, items);
        ArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(ArrayAdapter);
        categorySpinner.setSelection(0);


        create_menu_dialog.setView(CreateView);
        create_menu_dialog.setIcon(R.drawable.ic_restaurant_menu_black_24dp);


        imageButton = (ImageButton) CreateView.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button clicked" + "\n");

                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (photoCaptureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(photoCaptureIntent, requestCode);
                }


            }
        });

        create_menu_dialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                EditText createName = (EditText) CreateView.findViewById(R.id.MenuNameTextView);
                EditText createCalories = (EditText) CreateView.findViewById(R.id.caloriesTextView);
                EditText createUnitPrice = (EditText) CreateView.findViewById(R.id.UnitPriceTextView);
                EditText createPrepTime = (EditText) CreateView.findViewById(R.id.prepTimeTextView);

                final Menu menu = new Menu();
                menu.setName(createName.getText().toString())
                        .setUuid(UUID.randomUUID().toString())
                        .setImage("https://firebasestorage.googleapis.com/v0/b/foodordersystem-68732.appspot.com/o/foodicon.png?alt=media&token=da6db255-a8bc-4e6b-8a97-c3ab71db2e06")
                        .setCategory(categorySpinner.getSelectedItem().toString())
                        .setCalories(Integer.parseInt(createCalories.getText().toString()))
                        .setUnitprice(Integer.parseInt(createUnitPrice.getText().toString()))
                        .setPreptime(Integer.parseInt(createPrepTime.getText().toString()));


                //Long tsLong = System.currentTimeMillis()/1000;
                final String uniqueId = String.valueOf(maxKey + 1);

                menuDatabase.child(uniqueId)
                        .setValue(menu)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                final String imageID = uniqueId + ".jpg";
                                StorageReference mountainsRef = storageRef.child(imageID);

                                imageButton.setDrawingCacheEnabled(true);
                                imageButton.buildDrawingCache();

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();

                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        storageRef.child(imageID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                menuDatabase.child(uniqueId).child("image").setValue(uri.toString());
                                                Toast.makeText(AdminDashboardActivity.this, "menu created!", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                exception.printStackTrace();
                                            }
                                        });

                                    }
                                });

                                //Toast.makeText(AdminDashboardActivity.this, "menu created!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminDashboardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                dialogInterface.dismiss();
            }
        });

        create_menu_dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        adapter.notifyDataSetChanged();
        create_menu_dialog.show();

    }
}
