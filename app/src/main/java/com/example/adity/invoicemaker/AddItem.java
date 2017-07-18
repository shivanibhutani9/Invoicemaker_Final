package com.example.adity.invoicemaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddItem extends AppCompatActivity {

    EditText descrip,HSN,cost,quant,sgst,cgst,igst;
    TextView amt;
    Double Cost;
    Integer sg,cg,ig;
    Integer quanti;
    String description,HSNcode,unitcost,quantity,amount,Sgst,Cgst,Igst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        descrip=(EditText)findViewById(R.id.description);
        HSN=(EditText)findViewById(R.id.HSNcode);
        sgst=(EditText)findViewById(R.id.SGSTcodee);
        cgst=(EditText)findViewById(R.id.CGSTcodee);
        igst=(EditText)findViewById(R.id.IGSTcodee);
        cost=(EditText)findViewById(R.id.cost);
        quant=(EditText)findViewById(R.id.quant);
        amt=(TextView)findViewById(R.id.amt);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button save=(Button)findViewById(R.id.save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description=descrip.getText().toString();
                HSNcode=HSN.getText().toString();
                unitcost=cost.getText().toString();
                Sgst=sgst.getText().toString();
                Cgst=cgst.getText().toString();
                Igst=igst.getText().toString();
                quantity=quant.getText().toString();

                quanti = Integer.parseInt(quant.getText().toString());
                Cost = Double.parseDouble(cost.getText().toString());

                Double l=(quanti*Cost);
                amt.setText("" + quanti * Cost);
                sg=Integer.parseInt(Sgst);
                cg=Integer.parseInt(Cgst);
                ig=Integer.parseInt(Igst);



                amount=amt.getText().toString();

                Intent i=new Intent();
                i.putExtra("description",description);
                i.putExtra("HSNcode",HSNcode);

                i.putExtra("Sgst",Sgst);
                i.putExtra("Cgst",Cgst);
                i.putExtra("Igst",Igst);

                i.putExtra("unitcost",unitcost);
                i.putExtra("quantity",quantity);
                i.putExtra("amount",amount);
                setResult(2,i);

                finish();
            }
        });


        cost.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {

                    if(cost.getText().equals(""))
                        cost.setText("0.0");
                }
                else if(hasFocus) {
                    if(cost.getText().equals("0.0"))
                        cost.setText("");
                }




            }
        });


        quant.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {

                    if(quant.getText().equals(""))
                        quant.setText("0.0");
                }
                else if(hasFocus) {
                    if(quant.getText().equals("0"))
                        quant.setText("");
                }

            }
        });







    }
}
