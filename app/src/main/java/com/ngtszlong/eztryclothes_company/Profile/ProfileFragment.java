package com.ngtszlong.eztryclothes_company.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ngtszlong.eztryclothes_company.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    ImageView imageView;
    EditText edt_email;
    EditText edt_name;
    CardView cardView;

    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    int PICK_IMAGE_REQUEST = 10001;
    Uri filePath;
    Uri geturi;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle(getText(R.string.company_profile));
        LoadLocale();

        imageView = view.findViewById(R.id.img_pro_image);
        edt_email = view.findViewById(R.id.edt_pro_email);
        edt_name = view.findViewById(R.id.edt_pro_name);
        cardView = view.findViewById(R.id.btn_pro_update);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imageView);
        }
        if (firebaseUser.getEmail() != null) {
            edt_email.setText(firebaseUser.getEmail());
        }
        if (firebaseUser.getDisplayName() != null) {
            edt_name.setText(firebaseUser.getDisplayName());
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        if (firebaseUser.getPhotoUrl() != null){
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(imageView);
        }

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.Pleasewait));


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(edt_name.getText().toString()).build();
                firebaseUser.updateProfile(request)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Profile image failed", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    Uri selectedImage = data.getData();
                    try {
                        String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
                        Cursor cur = getActivity().managedQuery(selectedImage, orientationColumn, null, null, null);
                        int orientation = -1;
                        if (cur != null && cur.moveToFirst()) {
                            orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                        }
                        InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
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
                        imageView.setImageBitmap(bitmap);
                        handleUpload(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://eztryclothes-3b490.appspot.com");
        final StorageReference reference = storageReference
                .child("CompanyLogo")
                .child(uid + ".png");

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

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "onSuccess: " + uri);
                geturi = uri;
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(geturi).build();
                firebaseUser.updateProfile(request)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Profile image failed", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Setting", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void LoadLocale() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }
}
