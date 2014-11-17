package newshoppinghelper.tzachsolomon.com.shoppinghelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentCompare extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText editTextProductLeftName;
    private EditText editTextLeftNumberOfUnits;
    private EditText editTextLeftGramPerUnit;
    private EditText editTextLeftTotalPrice;
    private EditText editTextLeftDiscountOnSecondItem;
    private EditText editTextLeftTotalWeight;

    private EditText editTextProductRightName;
    private EditText editTextRightNumberOfUnits;
    private EditText editTextRightGramPerUnit;
    private EditText editTextRightTotalPrice;
    private EditText editTextRightDiscountOnSecondItem;
    private EditText editTextRightTotalWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compare, container, false);

        initEditTexts(rootView);
        initButtons(rootView);
        return rootView;
    }

    private void initEditTexts(View rootView) {
        editTextProductLeftName = (EditText)rootView.findViewById(R.id.editTextProductLeftName);
        editTextLeftNumberOfUnits = (EditText)rootView.findViewById(R.id.editTextLeftNumberOfUnits);
        editTextLeftGramPerUnit = (EditText)rootView.findViewById(R.id.editTextLeftGramPerUnit);
        editTextLeftTotalPrice = (EditText)rootView.findViewById(R.id.editTextLeftTotalPrice);
        editTextLeftDiscountOnSecondItem = (EditText)rootView.findViewById(R.id.editTextLeftDiscountOnSecondItem);
        editTextLeftTotalWeight = (EditText)rootView.findViewById(R.id.editTextLeftTotalWeight);

        editTextProductRightName = (EditText)rootView.findViewById(R.id.editTextProductRightName);
        editTextRightNumberOfUnits = (EditText)rootView.findViewById(R.id.editTextRightNumberOfUnits);
        editTextRightGramPerUnit = (EditText)rootView.findViewById(R.id.editTextRightGramPerUnit);
        editTextRightTotalPrice = (EditText)rootView.findViewById(R.id.editTextRightTotalPrice);
        editTextRightDiscountOnSecondItem = (EditText)rootView.findViewById(R.id.editTextRightDiscountOnSecondItem);
        editTextRightTotalWeight = (EditText)rootView.findViewById(R.id.editTextRightTotalWeight);

        editTextLeftGramPerUnit.setOnFocusChangeListener(this);
        editTextLeftNumberOfUnits.setOnFocusChangeListener(this);
        editTextLeftDiscountOnSecondItem.setOnFocusChangeListener(this);

        editTextRightGramPerUnit.setOnFocusChangeListener(this);
        editTextRightNumberOfUnits.setOnFocusChangeListener(this);
        editTextRightDiscountOnSecondItem.setOnFocusChangeListener(this);


    }

    private void initButtons(View rootView) {
        Button buttonCompare = (Button)rootView.findViewById(R.id.buttonCompare);
        buttonCompare.setOnClickListener(this);

        // TODO: complete buttons undo and clear
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCompare:
                compareItems();
                break;
        }

    }

    private void compareItems() {

        try {
            String leftProductName = editTextProductLeftName.getText().toString();
            float leftNumberOfUnits = getFloat(editTextLeftNumberOfUnits, "Left number of units is missing");
            float leftGramPerUnit = getFloat(editTextLeftGramPerUnit, "Left gram per unit is missing");
            float leftTotalPrice = getFloat(editTextLeftTotalPrice, "Left total price is missing");
            String rightProductName = editTextProductRightName.getText().toString();
            float rightNumberOfUnits = getFloat(editTextRightNumberOfUnits, "Right number of units is missing");
            float rightGramPerUnit = getFloat(editTextRightGramPerUnit, "Right gram per unit is missing");
            float rightTotalPrice = getFloat(editTextRightTotalPrice, "Right total price is missing");
            String leftDiscountOnSecondItem = editTextLeftDiscountOnSecondItem.getText().toString();
            String rightDiscountOnSecondItem = editTextRightDiscountOnSecondItem.getText().toString();
            float leftAmount;
            float rightAmount;



            if (leftProductName == null) {
                leftProductName = "on the Left ";
            } else if (leftProductName.isEmpty()) {
                leftProductName = "on the Left ";
            }

            if (rightProductName == null) {
                rightProductName = "on the Right ";
            } else if (rightProductName.isEmpty()) {
                rightProductName = "on the Right ";
            }

            leftAmount = leftNumberOfUnits * leftGramPerUnit;
            rightAmount = rightNumberOfUnits * rightGramPerUnit;

            // In case the 2nd Left item has a discount
            if ( !leftDiscountOnSecondItem.isEmpty()){
                // adding the 2nd product after the discount to the total price
                leftTotalPrice += (leftTotalPrice * (1 - (Float.valueOf(leftDiscountOnSecondItem) / 100)));

                // doubling the amount
                leftAmount *= 2;

            }

            // In case the 2nd Right item has a discount
            if ( !rightDiscountOnSecondItem.isEmpty()){
                // adding the 2nd product after the discount to the total price
                rightTotalPrice += (rightTotalPrice * (1 - (Float.valueOf(rightDiscountOnSecondItem) / 100)));

                // doubling the amount
                rightAmount *= 2;

            }

            float compareLeft = leftTotalPrice / leftAmount;
            float compareRight = rightTotalPrice / rightAmount;

            String message;

            if (compareLeft < compareRight) {
                message =  "Product " + leftProductName + " is cheaper";
            } else if (compareRight < compareLeft) {
                message =  "Product " + rightProductName + " is cheaper";
            } else {
                message = "Both products are equal at price per value";
            }

            // display the message
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage(message).setTitle("Compare result");
            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();


        }catch (NumberFormatException e){

        }

    }

    private Float getFloat(EditText editText, String exceptionMessage) {
        float ret;
        try{
            ret = Float.valueOf(editText.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this.getActivity(),exceptionMessage,Toast.LENGTH_SHORT).show();
            throw e;
        }

        return ret;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.editTextLeftNumberOfUnits:
            case R.id.editTextLeftGramPerUnit:
            case R.id.editTextLeftDiscountOnSecondItem:
                calcTotalWeight(editTextLeftNumberOfUnits,editTextLeftGramPerUnit,editTextLeftTotalWeight,editTextLeftDiscountOnSecondItem);
                break;
            case R.id.editTextRightNumberOfUnits:
            case R.id.editTextRightGramPerUnit:
            case R.id.editTextRightDiscountOnSecondItem:
                calcTotalWeight(editTextRightNumberOfUnits,editTextRightGramPerUnit,editTextRightTotalWeight, editTextRightDiscountOnSecondItem);
                break;
        }
    }

    private void calcTotalWeight(EditText editTextNumberOfUnits, EditText editTextGramPerUnit, EditText editTextTotalWeight, EditText discountOnSecondItem) {
        try{
            float numberOfUnits = Float.valueOf(editTextNumberOfUnits.getText().toString());
            float gramPerUnit = Float.valueOf(editTextGramPerUnit.getText().toString());
            float total = numberOfUnits * gramPerUnit;

            if ( !discountOnSecondItem.getText().toString().isEmpty()){
                total *= 2;
            }

            if ( total > 1000){
                editTextTotalWeight.setText(total / 1000 +" KG");
            }else{
                editTextTotalWeight.setText(total +" gram");
            }


        }catch (NumberFormatException e){
            editTextTotalWeight.setText("");
        }

    }
}
