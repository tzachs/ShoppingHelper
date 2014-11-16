package newshoppinghelper.tzachsolomon.com.shoppinghelper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentDiscount extends Fragment implements View.OnClickListener {
    private EditText editTextOriginalPrice;
    private EditText editTextDiscount1;
    private EditText editTextDiscount2;
    private EditText editTextDiscount3;
    private TextView textViewDiscount1;
    private TextView textViewDiscount2;
    private TextView textViewDiscount3;
    private Button buttonCalculateDiscount;
    private TextView textViewPriceAfterDiscount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_discount, container, false);

        initEditText(rootView);
        initTextView(rootView);
        initButton(rootView);

        return rootView;
    }

    private void initButton(View rootView) {
        buttonCalculateDiscount = (Button) rootView.findViewById(R.id.buttonCalculateDiscount);
        buttonCalculateDiscount.setOnClickListener(this);
    }

    private void initTextView(View rootView) {

        textViewDiscount1 = (TextView) rootView.findViewById(R.id.textViewDiscount1);
        textViewDiscount2 = (TextView) rootView.findViewById(R.id.textViewDiscount2);
        textViewDiscount3 = (TextView) rootView.findViewById(R.id.textViewDiscount3);
        textViewPriceAfterDiscount = (TextView) rootView.findViewById(R.id.textViewPriceAfterDiscount);
    }

    private void initEditText(View rootView) {

        editTextOriginalPrice = (EditText) rootView.findViewById(R.id.editTextOriginalPrice);
        editTextDiscount1 = (EditText) rootView.findViewById(R.id.editTextDiscount1);
        editTextDiscount2 = (EditText) rootView.findViewById(R.id.editTextDiscount2);
        editTextDiscount3 = (EditText) rootView.findViewById(R.id.editTextDiscount3);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCalculateDiscount:
                buttonCalculateDiscountClicked();
                break;
        }
    }

    private void buttonCalculateDiscountClicked() {
        String originalPriceString = editTextOriginalPrice.getText().toString();
        Float originalPrice;

        if ( originalPriceString.isEmpty()){
            Toast.makeText(this.getActivity(),"Original price is missing",Toast.LENGTH_SHORT).show();
        }else{
            originalPrice = Float.valueOf(originalPriceString);
            originalPrice = calculateDiscount(editTextDiscount1.getText().toString(), originalPrice, textViewDiscount1);
            originalPrice = calculateDiscount(editTextDiscount2.getText().toString(), originalPrice, textViewDiscount2);
            originalPrice = calculateDiscount(editTextDiscount3.getText().toString(), originalPrice, textViewDiscount3);

            textViewPriceAfterDiscount.setText("Total Price after discount(s): " + originalPrice);
        }

    }

    private Float calculateDiscount(String discountString, Float originalPrice, TextView textViewDiscount) {
        Float discount;
        if (!discountString.isEmpty()){
            discount = Float.valueOf(discountString);
            originalPrice = originalPrice * (1 - discount / 100);
            textViewDiscount.setText(Float.toString(originalPrice));
        }
        return originalPrice;
    }
}


