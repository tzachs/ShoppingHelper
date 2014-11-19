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

import java.util.Stack;

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
    private Button buttonCompare;
    private Button buttonClear;
    private Button buttonUndo;

    private Stack<String> mUndo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_compare, container, false);

        mUndo = new Stack<String>();

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
        buttonCompare = (Button)rootView.findViewById(R.id.buttonCompare);
        buttonCompare.setOnClickListener(this);

        buttonClear = (Button)rootView.findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        buttonUndo = (Button)rootView.findViewById(R.id.buttonUndo);
        buttonUndo.setOnClickListener(this);
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

            case R.id.buttonClear:
                buttonClearClicked();
                break;

            case R.id.buttonUndo:
                buttonUndoClicked();
                break;
        }

    }

    private void buttonUndoClicked() {

        if (!mUndo.isEmpty()){
            editTextRightDiscountOnSecondItem.setText(mUndo.pop());
            editTextLeftDiscountOnSecondItem.setText(mUndo.pop());
            editTextRightTotalPrice.setText(mUndo.pop());
            editTextRightGramPerUnit.setText(mUndo.pop());
            editTextRightNumberOfUnits.setText(mUndo.pop());
            editTextProductLeftName.setText(mUndo.pop());
            editTextProductRightName.setText(mUndo.pop());
            editTextLeftTotalPrice.setText(mUndo.pop());
            editTextLeftGramPerUnit.setText(mUndo.pop());
            editTextLeftNumberOfUnits.setText(mUndo.pop());
            editTextProductLeftName.setText(mUndo.pop());
        }
    }

    private void buttonClearClicked() {

        editTextProductLeftName.setText("");
        editTextLeftNumberOfUnits.setText("");
        editTextLeftGramPerUnit.setText("");
        editTextLeftTotalPrice.setText("");
        editTextLeftDiscountOnSecondItem.setText("");
        editTextLeftTotalWeight.setText("");

        editTextProductRightName.setText("");
        editTextRightNumberOfUnits.setText("");
        editTextRightGramPerUnit.setText("");
        editTextRightTotalPrice.setText("");
        editTextRightDiscountOnSecondItem.setText("");
        editTextRightTotalWeight.setText("");

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

            mUndo.push(editTextProductLeftName.getText().toString());
            mUndo.push(editTextLeftNumberOfUnits.getText().toString());
            mUndo.push(editTextLeftGramPerUnit.getText().toString());
            mUndo.push(editTextLeftTotalPrice.getText().toString());
            mUndo.push(editTextProductRightName.getText().toString());
            mUndo.push(editTextProductLeftName.getText().toString());
            mUndo.push(editTextRightNumberOfUnits.getText().toString());
            mUndo.push(editTextRightGramPerUnit.getText().toString());
            mUndo.push(editTextRightTotalPrice.getText().toString());
            mUndo.push(editTextLeftDiscountOnSecondItem.getText().toString());
            mUndo.push(editTextRightDiscountOnSecondItem.getText().toString());

            // display the message
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage(message).setTitle("Compare result");
            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();


        }catch (NumberFormatException e){
            e.printStackTrace();
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
