package com.example.isangeet;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    Button btnNext;
    EditText firstname;
    EditText lastname;
    EditText password;
    EditText repassword;
    FirebaseAuth mAuth;


    EditText email;
    Button btnNext1;
    Button btnNext2;
    EditText sendto, subject, body;
   // ProgressBar progressbar;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Registration.this, MainActivity.class);
            startActivity(intent);
        }
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email =    findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        btnNext = findViewById(R.id.btnNext);
       // progressbar = findViewById(R.id.progressbar);

//        defineButtons();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // progressbar.setVisibility(View.VISIBLE);
                String email1,password1;

                email1 = String.valueOf(email.getText());
                password1 = String.valueOf(password.getText());


                if(TextUtils.isEmpty(email1))
                {
                    Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password1))
                {
                    Toast.makeText(Registration.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    return;
                }



                mAuth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {

                               // progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {


                                    Toast.makeText(Registration.this, "Registered Succesfully.",
                                            Toast.LENGTH_SHORT).show();


                                } else {


                                    Toast.makeText(Registration.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });












                boolean result  = checkDataEntered();
                if((password.getText().toString()).equals(repassword.getText().toString()) && result)
                {
                    if((password.getText().toString()).equals(""))
                    {
                        Toast.makeText(Registration.this, "Invalid Entry", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(Registration.this, Login.class);
                        startActivity(intent);
                    }
                }




            }


        });

        //intent end
        //email
        sendto = findViewById(R.id.email);
        subject = findViewById(R.id.firstname);
        body = findViewById(R.id.lastname);
        btnNext2 = findViewById(R.id.btnNext2);

        btnNext2.setOnClickListener(view -> {
            String emailsend = sendto.getText().toString();
            String emailsubject = subject.getText().toString();
            String emailbody = body.getText().toString();

            // define Intent object with action attribute as ACTION_SEND
            Intent intent = new Intent(Intent.ACTION_SEND);

            // add three fields to intent using putExtra function
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailsend});
            intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
            intent.putExtra(Intent.EXTRA_TEXT, emailbody);

            // set type of intent
            intent.setType("message/rfc822");

            // startActivity with intent with chooser as Email client using createChooser function
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
        });
        //email end//


    }



    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    boolean checkDataEntered() {

        int count = 0;
        if (isEmpty(firstname)) {
            firstname.setError("First name is required!");
            count++;
        }

        if (isEmpty(lastname)) {
            lastname.setError("Last name is required!");
            count++;

        }

        if (isEmail(email) == false) {
            email.setError("Enter valid email!");
            count++;
        }

        if (isEmpty(password)) {
            password.setError("Enter valid password!");
            count++;
        }

        if (isEmpty(repassword)) {
            repassword.setError("Enter valid password!");
            count++;
        }
        if(count != 0){
            return false;
        }
        return true;
    }









}