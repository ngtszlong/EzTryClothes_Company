package com.ngtszlong.eztryclothes_company.Clothes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ngtszlong.eztryclothes_company.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edt_name_chi;
    EditText edt_name_eng;
    RadioButton rb_male;
    RadioButton rb_female;
    Spinner spinner_type;
    EditText edt_color_chi;
    EditText edt_color_eng;
    EditText edt_price;
    EditText edt_discourt;
    EditText edt_quantity;
    RadioButton rb_xl;
    RadioButton rb_l;
    RadioButton rb_m;
    RadioButton rb_s;
    RadioButton rb_xs;
    EditText edt_material_chi;
    EditText edt_material_eng;
    EditText edt_description_chi;
    EditText edt_description_eng;
    TextView txt_date;
    CardView btn_Add;
    Toolbar toolbar;
    CardView upload_image;
    CardView upload_try;
    String date;
    FirebaseAuth fAuth;
    FirebaseUser user;
    Product product;
    String gender;
    String uploadimage;
    String uploadtry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getcurrenttime();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        product = new Product();

        edt_name_chi = findViewById(R.id.edt_name_chi);
        edt_name_eng = findViewById(R.id.edt_name_eng);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        spinner_type = findViewById(R.id.spinner_type);
        edt_color_chi = findViewById(R.id.edt_color_chi);
        edt_color_eng = findViewById(R.id.edt_color_eng);
        edt_price = findViewById(R.id.edt_price);
        edt_discourt = findViewById(R.id.edt_discount);
        rb_xl = findViewById(R.id.rb_xl);
        rb_l = findViewById(R.id.rb_l);
        rb_m = findViewById(R.id.rb_m);
        rb_s = findViewById(R.id.rb_s);
        rb_xs = findViewById(R.id.rb_xs);
        edt_material_chi = findViewById(R.id.edt_material_chi);
        edt_material_eng = findViewById(R.id.edt_material_eng);
        edt_description_chi = findViewById(R.id.edt_description_chi);
        edt_description_eng = findViewById(R.id.edt_description_eng);
        txt_date = findViewById(R.id.txt_date);
        btn_Add = findViewById(R.id.btn_Add);
        upload_image = findViewById(R.id.btn_upload_photo);
        upload_try = findViewById(R.id.btn_upload_photo_for_try);
        edt_quantity = findViewById(R.id.edt_quantity);

        toolbar = findViewById(R.id.tb_addproduct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Product");

        rb_male.setChecked(true);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);
        spinner_type.setOnItemSelectedListener(this);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txt_date.setText(sdf.format(myCalendar.getTime()));
            }
        };

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddProductActivity.this, dateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        upload_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void uploaddata() {
        String uid = user.getUid();
        String companyname = user.getDisplayName();
        product.setUid(uid);
        product.setNo(date);
        product.setColor_Chi(edt_color_chi.getText().toString());
        product.setColor_Eng(edt_color_eng.getText().toString());
        product.setCompany(companyname);
        product.setDescription_Chi(edt_description_chi.getText().toString());
        product.setDescription_Eng(edt_description_eng.getText().toString());
        product.setDiscount(edt_discourt.getText().toString());
        product.setGender(gender);
        product.setImage(uploadimage);
        //product.setL();
        //product.setM();
        //product.setXS();
        //product.setXL();
        product.setType(spinner_type.getSelectedItem().toString());
        product.setQuantity(edt_quantity.getText().toString());
        product.setName_Chi(edt_name_chi.getText().toString());
        product.setName_Eng(edt_name_eng.getText().toString());
        //product.setS();
        //product.setXS();
        product.setMaterial_Eng(edt_material_eng.getText().toString());
        product.setPrice(edt_price.getText().toString());
        product.setTry_photo(uploadtry);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Clothes");
        reference.child(date).setValue(product);
        Toast.makeText(AddProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
    }

    public void getcurrenttime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
        date = sDateFormat.format(new java.util.Date());
    }
}
