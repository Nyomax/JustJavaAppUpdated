package com.example.echomax.justjavaappupdated;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int pricePerCup = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //this method calculates the price of the quantity ordered

    private int calculatePrice(Boolean addWhippedCream,Boolean addChocolate) {
       //int whippedCreamPrice=1; initialises whippedCreamPrice
       //int chocolatePrice=2;    initialises chocolatePrice


        int price =  pricePerCup * quantity;
        if(addWhippedCream && addChocolate ){
            pricePerCup=pricePerCup+3;
            return price;
        }
        else if(addWhippedCream){
            pricePerCup = pricePerCup+1;
            return price;
        }
        else if(addChocolate){
            pricePerCup = pricePerCup+2;
            return price;
        }  else {

            return price;
        }

    }

    //this method  displays of the quantity
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);


    }  //this method  is triggered when the submit order button is clicked

    public void submitOrder(View view) {
        CheckBox addWhippedCreamCheckBox =  findViewById(R.id.whipped_cream_checked);

        Boolean addWhippedCream = addWhippedCreamCheckBox.isChecked();
        CheckBox addChocolateCheckBox = findViewById(R.id.chocolate_checked);
        Boolean addChocolate = addChocolateCheckBox.isChecked();
        int price= calculatePrice(addWhippedCream,addChocolate);
        String imputedText="";



        //this diplays the message

        String userInputEditText=displayEditText(imputedText);
        String message=orderSummary(price, addWhippedCream,addChocolate,userInputEditText);
        //creating an intent for the gmail app

        Intent myMailClient = new Intent(Intent.ACTION_SENDTO );
        myMailClient.setData(Uri.parse("mailto:")); //onlyemail apps should handle this
        myMailClient.putExtra(Intent.EXTRA_SUBJECT,"Just Java Order for " + userInputEditText);
        myMailClient.putExtra(Intent.EXTRA_TEXT,message);
        if(myMailClient.resolveActivity(getPackageManager())!=null) {
            startActivity(myMailClient);
        }

        //displayMessage(message);



    }

    //This method displays the edited text from the user
    private String displayEditText(String imputedText) {
        EditText userInputEditText = findViewById(R.id.edit_text_view);
        imputedText= userInputEditText.getText().toString();
        Log.v("MainActivity",imputedText);

        return imputedText;

    }


    //*I subtracted this section of the code because I wanted to summary message to display on the Intent App - the email
    //in this case instead of the App.
   // private void displayMessage(String Message) {
       // TextView orderSummaryTextView = findViewById(R.id.text_view_orderSummary);
        //orderSummaryTextView.setText(Message);

   // }

    //this method gives the summary of what was ordered

    private String orderSummary(int price, Boolean addWhippedCream,Boolean addChocolate,String userInputEditText) {
        price = calculatePrice(addWhippedCream,addChocolate);
        String priceMessage = "Name:" + userInputEditText;//get solution later
        priceMessage += "\n" + getString(R.string.whipCheckBox_text) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.chocoCheckBox_text)+ addChocolate;
        priceMessage += "\n"+ getString(R.string.quantity_text) + quantity;
        priceMessage += "\n" +getString(R.string.total) + price;
                NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n"+ getString(R.string.thank_you);

        return priceMessage;
    }

    //this method increments the quantity
    public void increment(View view) {
        quantity += 1;
        if(quantity > 100){
            quantity = 100;
            Toast.makeText(getApplicationContext(), "You cannot place an order more than 100 cup",Toast.LENGTH_SHORT).show();
            return;
        }else

        displayQuantity(quantity);
    }
    //this method decrements the quantity

    public void decrement(View view) {
        quantity = quantity - 1;
        if(quantity < 1){
            quantity = 0;

            Toast.makeText(getApplicationContext(), "You cannot place an order less than 1 cup",Toast.LENGTH_SHORT).show();
            return;
        }else

        displayQuantity(quantity);
    }

}