package com.ngtszlong.eztryclothes_company.Clothes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ngtszlong.eztryclothes_company.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class EditClothesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "EditProductFragment";
    Toolbar toolbar;
    String No;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ArrayList<Product> productArrayList;

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
    CardView btn_upload;
    ImageView img_image;
    ImageView img_try;

    String no;
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
    String action ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clothes);

        toolbar = findViewById(R.id.tb_editproduct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getText(R.string.EditProduct));

        Intent intent = getIntent();
        No = intent.getStringExtra("no");

        edt_name_chi = findViewById(R.id.edt_edit_name_chi);
        edt_name_eng = findViewById(R.id.edt_edit_name_eng);
        rb_male = findViewById(R.id.rb_edit_male);
        rb_female = findViewById(R.id.rb_edit_female);
        spinner_type = findViewById(R.id.spinner_edit_type);
        edt_color_chi = findViewById(R.id.edt_edit_color_chi);
        edt_color_eng = findViewById(R.id.edt_edit_color_eng);
        edt_price = findViewById(R.id.edt_edit_price);
        edt_discourt = findViewById(R.id.edt_edit_discount);
        rb_xl = findViewById(R.id.rb_edit_xl);
        rb_l = findViewById(R.id.rb_edit_l);
        rb_m = findViewById(R.id.rb_edit_m);
        rb_s = findViewById(R.id.rb_edit_s);
        rb_xs = findViewById(R.id.rb_edit_xs);
        edt_material_chi = findViewById(R.id.edt_edit_material_chi);
        edt_material_eng = findViewById(R.id.edt_edit_material_eng);
        edt_description_chi = findViewById(R.id.edt_edit_description_chi);
        edt_description_eng = findViewById(R.id.edt_edit_description_eng);
        txt_date = findViewById(R.id.txt_edit_date);
        btn_upload = findViewById(R.id.btn_edit_Update);
        edt_quantity = findViewById(R.id.edt_edit_quantity);
        img_image = findViewById(R.id.img_edit_image);
        img_try = findViewById(R.id.img_edit_try);

        rb_male.setChecked(true);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);
        spinner_type.setOnItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference().child("Clothes");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productArrayList = new ArrayList<Product>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product l = dataSnapshot1.getValue(Product.class);
                    if (firebaseUser.getUid().equals(l.getUid())) {
                        if (No.equals(l.getNo())) {
                            productArrayList.add(l);
                            putdata(l);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.keepSynced(true);
        img_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "IMAGE";
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        img_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action = "TRY";
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

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

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

    private void upload() {
        if (rb_male.isChecked()) {
            gender = "Male";
        } else if (rb_female.isChecked()) {
            gender = "Female";
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference().child("Clothes");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Product product = dataSnapshot1.getValue(Product.class);
                    if (firebaseUser.getUid().equals(product.getUid())) {
                        if (No.equals(product.getNo())) {
                            String uid = firebaseUser.getUid();
                            String companyname = firebaseUser.getDisplayName();
                            product.setUid(uid);
                            product.setNo(product.getNo());
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
                            //product.setType(spinner_type.getSelectedItem().toString());
                            product.setQuantity(edt_quantity.getText().toString());
                            product.setName_Chi(edt_name_chi.getText().toString());
                            product.setName_Eng(edt_name_eng.getText().toString());
                            product.setMaterial_Eng(edt_material_eng.getText().toString());
                            product.setPrice(edt_price.getText().toString());
                            if (filePath_img != null) {
                                product.setImage(filePath_img.toString());
                            }else{
                                product.setImage("");
                            }
                            if (filePath_try != null) {
                                product.setTry_photo(filePath_try.toString());
                            } else {
                                product.setTry_photo("");
                            }
                            product.setReleaseDate(txt_date.getText().toString());
                            mRef.child(product.getNo()).setValue(product);
                        }
                    }
                }
                Toast.makeText(EditClothesActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        onBackPressed();
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
                    .child(no + ".jpeg");

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
                    .child(no + ".png");

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
                }
            });
        } else if (action.equals("TRY")) {
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d(TAG, "onSuccess: " + uri);
                    filePath_try = uri;
                }
            });
        }
    }

    private void putdata(Product l) {
        edt_name_chi.setText(l.getName_Chi());
        edt_name_eng.setText(l.getName_Eng());
        if (l.getGender().equals("Male")) {
            rb_male.setChecked(true);
        } else if (l.getGender().equals("Female")) {
            rb_female.setChecked(true);
        }
        int index = 0;
        for (int i=0; i<spinner_type.getCount();i++){
            if (spinner_type.getItemAtPosition(i).equals(l.getType())){
                index = i;
            }
        }
        spinner_type.setSelection(index);
        edt_color_chi.setText(l.getColor_Chi());
        edt_color_eng.setText(l.getColor_Eng());
        edt_price.setText(l.getPrice());
        edt_discourt.setText(l.getDiscount());
        if (l.getXL().equals("Y")) {
            rb_xl.setChecked(true);
        }
        if (l.getL().equals("Y")) {
            rb_l.setChecked(true);
        }
        if (l.getM().equals("Y")) {
            rb_m.setChecked(true);
        }
        if (l.getS().equals("Y")) {
            rb_s.setChecked(true);
        }
        if (l.getXS().equals("Y")) {
            rb_xs.setChecked(true);
        }
        edt_material_chi.setText(l.getMaterial_Chi());
        edt_material_eng.setText(l.getMaterial_Eng());
        edt_description_chi.setText(l.getDescription_Chi());
        edt_description_eng.setText(l.getDescription_Eng());
        txt_date.setText(l.getReleaseDate());
        edt_quantity.setText(l.getQuantity());
        if (!l.getImage().equals("")) {
            Picasso.get().load(l.getImage()).into(img_image);
            filePath_img = Uri.parse(l.getImage());
        }
        if (!l.getTry_photo().equals("")) {
            Picasso.get().load(l.getTry_photo()).into(img_try);
            filePath_try = Uri.parse(l.getTry_photo());
        }
        no = l.getNo();
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
}
