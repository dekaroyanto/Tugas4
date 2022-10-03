package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPass, etConfPass, etHomePage, etAbout;
    private String sfullName, semail, spass, sconfPass, shomePage, sabout;
    private Button btnok;
    private CircleImageView gambar;
    private ImageView inputAvatar;
    private Uri avatarUri;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.text_fullname);
        etEmail = findViewById(R.id.text_email);
        etPass = findViewById(R.id.text_password);
        etConfPass = findViewById(R.id.text_confirm_password);
        etHomePage = findViewById(R.id.text_homepage);
        etAbout = findViewById(R.id.text_about);
        btnok = findViewById(R.id.button_ok);
        gambar = findViewById(R.id.image_profile);
        inputAvatar = findViewById(R.id.imageView);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sfullName = etFullName.getText().toString();
                semail = etEmail.getText().toString();
                spass = etPass.getText().toString();
                sconfPass = etConfPass.getText().toString();
                shomePage = etHomePage.getText().toString();
                sabout = etAbout.getText().toString();

                if (!Objects.equals(spass, sconfPass)) {
                    Toast.makeText(RegisterActivity.this,
                            "Konfirmasi password tidak cocok",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                Intent pindah = new Intent(RegisterActivity.this, ProfileActivity.class);

                pindah.putExtra("fullname", sfullName);
                pindah.putExtra("email", semail);
                pindah.putExtra("homepage", shomePage);
                pindah.putExtra("about", sabout);
                if(!Objects.equals(avatarUri, null)) {
                    pindah.putExtra("avatar", avatarUri);
                }

                startActivity(pindah);
            }
        });

        inputAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(RegisterActivity.this,
                    "Batal menambahkan gambar",
                    Toast.LENGTH_SHORT).show();

            return;
        } else if(requestCode == GALLERY_REQUEST_CODE) {
            if(!Objects.equals(data, null)) {
                try {
                    avatarUri = data.getData();
                    Bitmap avatarBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avatarUri);
                    gambar.setImageBitmap(avatarBitmap);
                } catch(IOException e) {
                    Toast.makeText(RegisterActivity.this,
                            "Tidak bisa memuat gambar",
                            Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }
}
