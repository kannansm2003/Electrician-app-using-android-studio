package com.example.ea;

import static androidx.constraintlayout.widget.StateSet.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] cricketer1=new String[]{"18 m steel box","18 m open box","16 m steel box","16 m open box","12 m steel box","12 m open box","8 m steel box","8 m open box","6 m steel box","6 m open box","4 m steel box","4 m open box","3 m steel box","3 m open box","2 m steel box","2 m open box","Single side steel Blade (1')","Double side steel Blade (2')","Star screw 3'","Star screw 2'","Star screw 1'","Star screw 2-1/2'","Star screw 2-3/4'","Star screw 1-1/2'","Star screw 1-1/4'","Star screw 1-3/4'","Star screw 3/4'","Star screw 1/2'","Insulation Wonder Tape Red","Insulation Wonder Tape Blue","Insulation Wonder Tape Green","Insulation Wonder Tape Yellow","Insulation Wonder Tape Black","Insulation Wire cutter","Tester","6A switch","6A Two way switch","6A Socket","Indicator","16A Switch","16A Two way Switch","16A Socket","AC Switch","32A DB Switch","2M Colling Bell Switch","1M Colling Bell Switch","Switch Dummy","USB Socket","3 Pin 6A top","3 Pin 16A top","1M Fan Regulator","2M Fan Regulator","4.0 sq.mm wire Red","4.0 sq.mm wire Black","4.0 sq.mm wire Yellow","4.0 sq.mm wire Blue","2.5 sq.mm wire Blue","2.5 sq.mm wire Red","2.5 sq.mm wire Black","2.5 sq.mm wire Yellow","2.5 sq.mm wire Green","1.5 sq.mm wire Blue","1.5 sq.mm wire Yellow","1.5 sq.mm wire Green","1.0 sq.mm wire Red","1.0 sq.mm wire BLack","1.0 sq.mm wire Blue","1.0 sq.mm wire Yellow","1.0 sq.mm wire Green","Home Theatre Wire","320 VRI copper wire","720 VRI copper wire","320 Aluminium wire","720 Aluminium wire","2 core Aluminum wire","6.0 sq.mm 2 core UG cable","10.0 sq.mm 2 core UG cable","10.0 sq.mm 4 core UG cable","16.0 sq.mm 4 core UG cable","20.00 sq.mm 4 core UG cable","24 sq.mm 2 core UG cable","Tata Stay wire","3/4' beengaan reel","1' beengaan reel","Two way insulation reel","Four way insulation reel","DC Kambi","1/2' PVC 15 kg coupler","1' PVC 15 kg coupler","3/4' GI service pipe","1' GI service pipe","3/4' GI nipple","1' GI nipple","3/4' GI Coupler","1' GI Coupler","3/4' GI Short Bend","1' GI Short Bend","3/4' GI Long Bend","1' GI Long Bend","3/4' GI Elbow","1' GI Elbow","3/4' GI H Nipple","1' GI H Nipple","1' PVC FTA","3/4' PVC FTA","1' GI clamp","3/4' GI clamp","1' GI Step clamp","3/4' GI step clamp","18 M frame","16 M frame","12 M frame","8 M frame","6 M frame","4 M frame","3 M frame","2 M frame","Pendant Holder","Batten Holder",""};
    private static final int WRITE_REQUEST_CODE = 1;
    LinearLayout layoutList;
    Button buttonAdd,create,sharepdf;
    EditText cust;
    Button buttonSubmitList;
    List<String> teamList=new ArrayList<>();
    ArrayList<Cricketer> cricketersList=new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutList=findViewById(R.id.Layout_list);
        buttonAdd=findViewById(R.id.button_add);
        buttonSubmitList=findViewById(R.id.button_submit_list);
        create=findViewById(R.id.share);
        cust=findViewById(R.id.cust_name);
        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfValidAndRead()) {
                    generatePdf(cricketersList);
                }
        }
            });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }
    private void generatePdf(List<Cricketer> cricketersList) {
        try {
            // Show file picker dialog to allow user to select save location
            String customername=cust.getText().toString();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateString = sdf.format(date);
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_TITLE, customername+"("+dateString+").pdf");
            startActivityForResult(intent, WRITE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating PDF file", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    EditText cust1=(EditText)findViewById(R.id.cust_name);
                    String custName = cust1.getText().toString();
                    // Write data to the selected file
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    Document document = new Document();
                    PdfWriter.getInstance(document, outputStream);
                    document.open();
                    PdfPTable table1 = new PdfPTable(2);
                    table1.setWidthPercentage(100);
                    table1.setWidths(new float[]{2, 1});
                    table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);

                    // Add customer name on the left side
                    PdfPCell customerCell = new PdfPCell(new Phrase("Customer Name: " + custName));
                    customerCell.setBorder(Rectangle.NO_BORDER);
                    table1.addCell(customerCell);

                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    // Add date on the right side
                    PdfPCell dateCell = new PdfPCell(new Phrase("Date: " + sdf.format(date)));
                    dateCell.setBorder(Rectangle.NO_BORDER);
                    dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table1.addCell(dateCell);



                    // Add table to document
                    document.add(table1);
                    // Create a table with 2 columns
                    PdfPTable table = new PdfPTable(3);
                    float[] columnWidths = {0.5f, 3f, 2f};
                    table.setWidths(columnWidths);
                    int n=1;
                    // Add table headers
                    table.addCell(new PdfPCell(new Phrase("S.no")));
                    table.addCell(new PdfPCell(new Phrase("Product Name")));
                    table.addCell(new PdfPCell(new Phrase("Number of pieces")));
                    // Add data to table
                    for (Cricketer cricketer : cricketersList) {
                        table.addCell(String.valueOf(n));
                        table.addCell(cricketer.getCricketerName());
                        table.addCell(cricketer.getTeamName());
                        n++;
                    }
                    // Add table to document
                    document.add(table);
                    sharePdf(uri);
                    document.close();
                    Toast.makeText(this, "PDF file saved to " + uri.getPath(), Toast.LENGTH_SHORT).show();
                    //sharePdf(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "Error occurred: " + e.getMessage());
                    Toast.makeText(this, "Error creating PDF file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void sharePdf(Uri uri) {
        //File file = new File(getExternalFilesDir(null), "sample.pdf");
        //Uri pdfFileUri = FileProvider.getUriForFile(this, "com.example.ea.fileprovider", file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share PDF file"));
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.button_add:
                addView();
                break;
            case R.id.button_submit_list:
                if(checkIfValidAndRead()){
                    Intent intent=new Intent(MainActivity.this,ActivityCricketers.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("list",cricketersList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
        //addView();
    }
    private boolean checkIfValidAndRead() {
        cricketersList.clear();
        boolean result = true;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            result = false;
        } else {
            for (int i = 0; i < layoutList.getChildCount(); i++) {
                View cricketerView = layoutList.getChildAt(i);
                AutoCompleteTextView editTextName = (AutoCompleteTextView) cricketerView.findViewById(R.id.edit_cricketer_name);
                EditText spinnerTeam = (EditText) cricketerView.findViewById(R.id.edit_cricketer_name1);
                Cricketer cricketer = new Cricketer();
                if (!editTextName.getText().toString().equals("")) {
                    cricketer.setCricketerName(editTextName.getText().toString());
                } else {
                    result = false;
                    break;
                }
                if (!spinnerTeam.getText().toString().equals("")) {
                    cricketer.setTeamName(spinnerTeam.getText().toString());
                } else {
                    result = false;
                    break;
                }
                cricketersList.add(cricketer);
            }
            if (cricketersList.size() == 0) {
                result = false;
                Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();
            } else if (!result) {
                Toast.makeText(this, "Enter", Toast.LENGTH_SHORT).show();
            }
        }
        return result;
    }
    private void addView() {
        final View cricketerView=getLayoutInflater().inflate(R.layout.row_add_product,null,false);
        AutoCompleteTextView editText = (AutoCompleteTextView) cricketerView.findViewById(R.id.edit_cricketer_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, cricketer1);
        editText.setAdapter(adapter);
        EditText spinnerTeam=(EditText)cricketerView.findViewById(R.id.edit_cricketer_name1);
        ImageView imageClose=(ImageView)cricketerView.findViewById(R.id.image_remove);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(cricketerView);
            }
        });
        layoutList.addView(cricketerView);
    }
    private void removeView(View view){
        layoutList.removeView(view);
    }
}