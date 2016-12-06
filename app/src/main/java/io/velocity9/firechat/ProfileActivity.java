package io.velocity9.firechat;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public CircleImageView photoImageView;

        public ProfileViewHolder(View v) {
            super(v);
            nameTextView = (TextView) itemView.findViewById(R.id.profile_name);
            photoImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }

    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        imageView = (ImageView) findViewById(R.id.profile_image);
        String photoUrl = mFirebaseUser.getPhotoUrl().toString();
        if (photoUrl == null) {
            imageView.setImageResource(R.drawable.ic_account_circle_black_36dp);
        } else {
            Profile profile = new Profile(mFirebaseUser.getDisplayName(), photoUrl);
            View view = findViewById(R.id.show_profile_layout);
            ProfileViewHolder viewHolder = new ProfileViewHolder(view);
            viewHolder.nameTextView.setText(profile.getName());
            if (photoUrl == null) {
                viewHolder.photoImageView.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this,
                        R.drawable.ic_account_circle_black_36dp));
            } else {
                Glide.with(ProfileActivity.this)
                        .load(photoUrl)
                        .into(viewHolder.photoImageView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_button:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
