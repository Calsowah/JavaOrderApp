/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox wchecked = (CheckBox) findViewById(R.id.wcheckbox);
        CheckBox cchecked = findViewById(R.id.ccheckbox);
        EditText name = findViewById(R.id.name_edit_text);
        int price = calculatePrice(wchecked.isChecked(), cchecked.isChecked());
        String priceMessage = createOrderSummary(name.getText(), price, wchecked.isChecked()
                , cchecked.isChecked());
////        String priceMessage =" Total = $" + price;
////        priceMessage = priceMessage + "\n Thank you!";
//        displayMessage(priceMessage);
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Order for "+ name);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }


    /**
     * Creates an order summary
     * @param price
     * @return a string containing the name, quantity, price, and a "thank you!" message
     */
    private String createOrderSummary(Editable name, int price,
                                      boolean wchecked, boolean cchecked){
        String message = "Name: "+name+ "\n";
        message += "Add whipped cream? "+ wchecked +"\n";
        message += "Add chocolate? "+ cchecked +"\n";
        message += "Quantity: "+quantity+"\n";
        message += "Total: $"+price+"\n";
        message += "Thank you!";
        return (message);
    }
    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean wcheck, boolean ccheck) {
        int price = 5;
        if (wcheck){
            price += 1;
        }
        if (ccheck){
            price += 2;
        }
        return price * quantity;
    }

    /**
     * This method is called when the plus button is clicked
     */
    public void increment(View view) {
        if (quantity+1>100){
            Toast toomuch = Toast.makeText
                    (this, "Too much!", Toast.LENGTH_SHORT);
            toomuch.show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity-1<1){
            Toast toosma = Toast.makeText
                    (this, "Really? Smh", Toast.LENGTH_SHORT);
            toosma.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number1) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number1);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}