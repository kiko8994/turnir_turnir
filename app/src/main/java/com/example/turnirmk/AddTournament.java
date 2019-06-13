package com.example.turnirmk;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.text.format.DateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AddTournament extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText editTextName;
    EditText editTextName3;
    Button buttonAdd;
    Button datum_gumb;
    EditText datum;
    public static String datum_za_ispis;
    private FloatingActionButton floatingActionButton;
    int day, year, month, dayFinal, yearFinal, monthFinal, hour, minute, hourFinal, minuteFinal;

    DatabaseReference databaseDogadaj;

    Session session = null;
    Context context = null;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser = mAuth.getInstance().getCurrentUser();
    String reciep = currentUser.getEmail();
    String[] username = reciep.split("@");

    ProgressBar progressBar3;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tournament);
        setTitle("Napravi turnir");
        databaseDogadaj = FirebaseDatabase.getInstance().getReference("dogadaj");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName3 = (EditText) findViewById(R.id.editTextName3);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        datum_gumb = (Button) findViewById(R.id.datum_gumb);
        datum = (EditText) findViewById(R.id.datum);

        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        context = this;

        

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj();
            }
        });

        datum_gumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTournament.this, AddTournament.this,
                year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }
    private void dodaj() {
        String name = editTextName.getText().toString().trim();
        String name3 = editTextName3.getText().toString().trim();
        String name4 = datum_za_ispis;

        if (name.isEmpty()) {
            editTextName.setError("Ime turnira mora biti upisano!");
            editTextName.requestFocus();
            return;
        }

        else if (name3.isEmpty()) {
            editTextName3.setError("Lokacija mora biti upisana!");
            editTextName3.requestFocus();
            return;
        }

        else if (name4.isEmpty()) {
            datum.setError("Termin održavanja turnira mora biti upisan!");
            datum.requestFocus();
            return;
        }

        else {
            String id = databaseDogadaj.push().getKey();
            Dogadaj dogadaj = new Dogadaj( name, username[0], name3, id, name4);
            databaseDogadaj.child(id).setValue(dogadaj);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("taplikacija9@gmail.com", "12AplikacijA@");
                }
            });

            progressBar3.setVisibility(View.VISIBLE);
            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1+1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTournament.this, AddTournament.this, hour, minute,
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;
        datum_za_ispis = yearFinal +" "+monthFinal +" "+dayFinal +" "+hourFinal +" "+minuteFinal;
        if(minuteFinal<10) {
            datum.setText(dayFinal + "." + monthFinal + "." + yearFinal + "." + " " + hourFinal + ":0" + minuteFinal);
        }
        else {
            datum.setText(dayFinal + "." + monthFinal + "." + yearFinal + "." + " " + hourFinal + ":" + minuteFinal);
        }
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String name = editTextName.getText().toString().trim();
            String name4 = datum.getText().toString().trim();

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("taplikacija9@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reciep));
                message.setSubject("Potvrda o uspješno organiziranom turniru");
                message.setContent("Uspjesno ste prijavili turnir " + name + " koji počinje " + name4 + "! Zahvaljujemo se na prijavi. Lijepi pozdrav od turnirMK aplikacije!", "text/html; charset=utf-8");

                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            editTextName.setText("");
            editTextName3.setText("");
            datum.setText("");
            progressBar3.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Message sent to " + reciep, Toast.LENGTH_LONG).show();
        }
    }
}
