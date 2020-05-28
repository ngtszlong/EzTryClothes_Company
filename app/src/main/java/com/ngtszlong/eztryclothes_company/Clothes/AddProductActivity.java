package com.ngtszlong.eztryclothes_company.Clothes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ngtszlong.eztryclothes_company.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "AddProductFragment";

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

    String XL = "N";
    String L = "N";
    String M = "N";
    String S = "N";
    String XS = "N";

    int PICK_IMAGE_REQUEST = 10001;
    Uri filePath_try;
    Uri filePath_img;
    Uri getFilePath_try;
    Uri getFilePath_img;
    String action;
    ProgressDialog progressDialog;

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
        getSupportActionBar().setTitle(getText(R.string.AddProduct));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.Pleasewait));

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
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txt_date.setText(sdf.format(myCalendar.getTime()));
            }
        };

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddProductActivity.this, R.style.DialogTheme, dateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "IMAGE";
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        upload_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "TRY";
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaddata();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (action.equals("IMAGE")) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                switch (resultCode) {
                    case RESULT_OK:
                        Uri getFilePath_img = data.getData();
                        try {
                            String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
                            Cursor cur = managedQuery(getFilePath_img, orientationColumn, null, null, null);
                            int orientation = -1;
                            if (cur != null && cur.moveToFirst()) {
                                orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                            }
                            InputStream imageStream = getContentResolver().openInputStream(getFilePath_img);
                            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                            switch (orientation) {
                                case 90:
                                    bitmap = rotateImage(bitmap, 90);
                                    break;
                                case 180:
                                    bitmap = rotateImage(bitmap, 180);
                                    break;
                                case 270:
                                    bitmap = rotateImage(bitmap, 270);
                                    break;
                                default:
                                    break;
                            }
                            progressDialog.show();
                            handleUpload(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        } else if (action.equals("TRY")) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                switch (resultCode) {
                    case RESULT_OK:
                        Uri getFilePath_try = data.getData();
                        try {
                            String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
                            Cursor cur = managedQuery(getFilePath_try, orientationColumn, null, null, null);
                            int orientation = -1;
                            if (cur != null && cur.moveToFirst()) {
                                orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                            }
                            InputStream imageStream = getContentResolver().openInputStream(getFilePath_try);
                            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                            switch (orientation) {
                                case 90:
                                    bitmap = rotateImage(bitmap, 90);
                                    break;
                                case 180:
                                    bitmap = rotateImage(bitmap, 180);
                                    break;
                                case 270:
                                    bitmap = rotateImage(bitmap, 270);
                                    break;
                                default:
                                    break;
                            }
                            progressDialog.show();
                            handleUpload(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    private void handleUpload(Bitmap bitmap) {
        if (action.equals("IMAGE")) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://eztryclothes-3b490.appspot.com");
            final StorageReference reference = storageReference
                    .child("productImage")
                    .child(date + ".jpeg");

            reference.putBytes(baos.toByteArray())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            getDownloadUrl(reference);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: ", e.getCause());
                        }
                    });
        } else if (action.equals("TRY")) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://eztryclothes-3b490.appspot.com");
            final StorageReference reference = storageReference
                    .child("productTry")
                    .child(date + ".png");

            reference.putBytes(baos.toByteArray())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            getDownloadUrl(reference);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: ", e.getCause());
                        }
                    });
        }

    }

    private void getDownloadUrl(StorageReference reference) {
        if (action.equals("IMAGE")) {
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d(TAG, "onSuccess: " + uri);
                    filePath_img = uri;
                    progressDialog.dismiss();
                }
            });
        } else if (action.equals("TRY")) {
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d(TAG, "onSuccess: " + uri);
                    filePath_try = uri;
                    progressDialog.dismiss();
                }
            });
        }
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
        if (rb_xl.isChecked()) {
            XL = "Y";
        }
        if (rb_l.isChecked()) {
            L = "Y";
        }
        if (rb_m.isChecked()) {
            M = "Y";
        }
        if (rb_s.isChecked()) {
            S = "Y";
        }
        if (rb_xs.isChecked()) {
            XS = "Y";
        }
        if (rb_male.isChecked()) {
            gender = "Male";
        }
        if (rb_female.isChecked()) {
            gender = "Female";
        }
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
        product.setL(L);
        product.setM(M);
        product.setXS(XS);
        product.setXL(XL);
        product.setS(S);
        product.setType(spinner_type.getSelectedItem().toString());
        product.setQuantity(edt_quantity.getText().toString());
        product.setName_Chi(edt_name_chi.getText().toString());
        product.setName_Eng(edt_name_eng.getText().toString());
        product.setMaterial_Eng(edt_material_eng.getText().toString());
        product.setPrice(edt_price.getText().toString());
        if (filePath_img != null) {
            product.setImage(filePath_img.toString());
        } else {
            product.setImage("");
        }
        if (filePath_try != null) {
            product.setTry_photo(filePath_try.toString());
        } else {
            product.setTry_photo("");
        }
        product.setReleaseDate(txt_date.getText().toString());
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
