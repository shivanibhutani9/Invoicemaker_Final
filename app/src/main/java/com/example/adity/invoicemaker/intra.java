package com.example.adity.invoicemaker;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by adity on 7/20/2017.
 */

public class intra {
    String invoice_id,invoice_date,user_com,user_add,user_gst,user_cp,client_com,client_add,client_state,client_zip,client_gst,total;
    ArrayList<String[]> items;
    ArrayList<String[]> GST;

public intra( String invoice_id,String invoice_date,String user_com,String user_add,String user_gst,String user_cp,String client_com,String client_add,String client_state,String client_zip,String client_gst, ArrayList<String[]> items, ArrayList<String[]> gsts,String total)
{
    this.invoice_id=invoice_id;
    this.invoice_date=invoice_date;
    this.user_com=user_com;
    this.user_add=user_add;
    this.user_gst=user_gst;
    this.user_cp=user_cp;
    this.client_com=client_com;
    this.client_add=client_add;
    this.client_state=client_state;
    this.client_zip=client_zip;
    this.client_gst=client_gst;
    this.items=items;
    this.GST=gsts;
    this.total=total;
}

    public void createpdf(File f)
    {
        com.itextpdf.text.Document doc=new com.itextpdf.text.Document(PageSize.A4.rotate(), 0f, 0f, 0f, 0f); //creating document
        String outPath=f.getPath();
        //location where the pdf will store
        try{
            PdfWriter.getInstance(doc,new FileOutputStream(outPath));
            doc.open();
                   /* PdfPTable table1=new PdfPTable(1);
                    PdfPCell pdfPCell=new PdfPCell(new Paragraph("Tax Invoice"));
                    pdfPCell.addElement(new Paragraph("Company Name"));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setColspan(4);
                    pdfPCell.setBackgroundColor(BaseColor.GRAY);
                    Chunk linebreak = new Chunk(new DottedLineSeparator());
                    doc.add(linebreak);
                    table1.addCell(pdfPCell);

                    doc.add(new Paragraph(t.getText().toString()));
                    doc.add(new Paragraph("hi how are you?"));
                    doc.add(table1);
                    table1.addCell("Company name");
                    table1.addCell("date");
                    doc.add(table1);*/



            doc.setMargins(0,0, 0,0);           // setting margin
            PdfPTable innertable = new PdfPTable(1);  //first table
            innertable.setWidthPercentage(100);
            //innertable.setWidths(new int[]{8, 12, 1, 4, 12});


// column 1
            PdfPCell cell = new PdfPCell(new Phrase("Tax Invoice",
                    FontFactory.getFont(FontFactory.COURIER_BOLD,20, Font.NORMAL, BaseColor.BLACK)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable.addCell(cell);




// column 2
            cell = new PdfPCell(new Paragraph(user_com,FontFactory.getFont(FontFactory.TIMES_BOLD,17,Font.NORMAL,BaseColor.BLACK)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable.addCell(cell);
// column 3
            cell = new PdfPCell(new Paragraph(user_add, FontFactory.getFont(FontFactory.TIMES_BOLD,17,Font.NORMAL,BaseColor.BLACK)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable.addCell(cell);
// column 4
            cell = new PdfPCell(new Paragraph(user_cp,FontFactory.getFont(FontFactory.TIMES_BOLD,17,Font.NORMAL,BaseColor.BLACK)));
            //cell.setPaddingLeft(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable.addCell(cell);
// spacing
            cell = new PdfPCell();
            cell.setColspan(5);
            cell.setFixedHeight(6);
            cell.setBorder(Rectangle.NO_BORDER);
            innertable.addCell(cell);
            doc.add(innertable);
            //      Chunk linebreak = new Chunk(new LineSeparator());
            //      doc.add(linebreak);

//Second table
            PdfPTable innertable2 = new PdfPTable(4);
            innertable2.setWidthPercentage(100);
            PdfPCell cell1 = new PdfPCell(new Phrase("Invoice no."));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(invoice_id));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell1.setPaddingLeft(20);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Transporter Details"));
            cell1.setBorder(Rectangle.NO_BORDER);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);


            cell1 = new PdfPCell(new Phrase("--"));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(2);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("Invoice date"));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase(invoice_date));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell1.setPaddingLeft(20);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Vehicle number"));
            cell1.setBorder(Rectangle.NO_BORDER);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);


            cell1 = new PdfPCell(new Phrase("--"));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(2);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("Reverse Charge (Y/N)"));
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("--"));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell1.setPaddingLeft(20);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Date of Supply"));
            cell1.setBorder(Rectangle.NO_BORDER);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);


            cell1 = new PdfPCell(new Phrase("--"));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(2);
            // cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            innertable2.addCell(cell1);

            doc.add(innertable2);
            //   Chunk linebreak1 = new Chunk(new LineSeparator());
            // doc.add(linebreak1);

            PdfPTable innertable3 = new PdfPTable(2);
            innertable3.setWidthPercentage(100);
            PdfPCell celll = new PdfPCell(new Phrase("RECIPIENT (BILL TO)",
                    FontFactory.getFont(FontFactory.COURIER,15,Font.NORMAL,BaseColor.BLACK)));
            celll.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable3.addCell(celll);

            celll = new PdfPCell(new Phrase("ADDRESS OF DELIVERY (SHIP TO)",
                    FontFactory.getFont(FontFactory.COURIER,15,Font.NORMAL,BaseColor.BLACK)));
            celll.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable3.addCell(celll);


            doc.add(innertable3);
            //Chunk linebreak2 = new Chunk(new LineSeparator());
            //doc.add(linebreak2);

            PdfPTable innertable4 = new PdfPTable(4);
            innertable4.setWidthPercentage(100);
            PdfPCell cell4 = new PdfPCell(new Phrase("Name :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase(client_com));
            innertable4.addCell(cell4);
            cell4 = new PdfPCell(new Phrase("Name :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("--------"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("Address :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase(client_add));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("Address :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("--------"));
            innertable4.addCell(cell4);

          /*  cell4 = new PdfPCell(new Phrase("____________"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase(""));
            innertable4.addCell(cell4);
            cell4 = new PdfPCell(new Phrase("______________"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase(""));
            innertable4.addCell(cell4);

*/
            cell4 = new PdfPCell(new Phrase("GSTIN :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase(client_gst));
            innertable4.addCell(cell4);
            cell4 = new PdfPCell(new Phrase("GSTIN :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("--------"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("State :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase(""+client_state));
            innertable4.addCell(cell4);
            cell4 = new PdfPCell(new Phrase("State :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("------"));
            innertable4.addCell(cell4);
            cell4 = new PdfPCell(new Phrase("code :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase(""+client_zip));
            innertable4.addCell(cell4);
            cell4 = new PdfPCell(new Phrase("code :"));
            innertable4.addCell(cell4);

            cell4 = new PdfPCell(new Phrase("--------"));
            cell4.setMinimumHeight(50f);
            innertable4.addCell(cell4);

            doc.add(innertable4);
            // Chunk linebreak3 = new Chunk(new LineSeparator());
            //doc.add(linebreak3);


            PdfPTable innertable5 = new PdfPTable(10);
            innertable5.setWidthPercentage(100);
            innertable5.setWidths(new int[]{4,11,4,4,3,4,6,5,5,4});
            PdfPCell cell5 = new PdfPCell(new Phrase("S.No:"));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("Product Description"));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("HSN code"));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("UQC"));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("Qty"));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("Rate"));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("Taxable Value"));
            innertable5.addCell(cell5);
               /* PdfPTable nested = new PdfPTable(1);
                nested.addCell("SGST");
                nested.addCell("");
                nested.addCell("Rate");
                nested.addCell("Amnt");
                PdfPCell nesthousing = new PdfPCell(nested);

                innertable5.addCell(nesthousing);
                PdfPTable nested2 = new PdfPTable(1);
                nested2.addCell("CGST");
                nested2.addCell("");
                nested2.addCell("Rate");
                nested2.addCell("Amnt");
                PdfPCell nesthousing2 = new PdfPCell(nested2);
                innertable5.addCell(nesthousing2);
                PdfPTable nested3 = new PdfPTable(1);
                nested3.addCell("IGST");
                nested3.addCell("");
                nested3.addCell("Rate");
                nested3.addCell("Amnt");
                PdfPCell nesthousing3 = new PdfPCell(nested3);
                innertable5.addCell(nesthousing3);
*/

            cell5 = new PdfPCell(new Phrase("SGST"));
            innertable5.addCell(cell5);

            cell5 = new PdfPCell(new Phrase("CGST"));
            innertable5.addCell(cell5);


            cell5 = new PdfPCell(new Phrase("Total"));
            cell5.setMinimumHeight(10f);
            innertable5.addCell(cell5);

            //first item


            for(int i=0;i<items.size();i++)
            {
                String item[]=items.get(i);
                String gsco[]=GST.get(i);
                cell5 = new PdfPCell(new Phrase(i+1));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase(item[0]));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase(item[1]));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase("     "));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase(item[5]));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase(item[4]));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase("      "));
                innertable5.addCell(cell5);
              /*  cell5 = new PdfPCell(new Phrase("      "));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase("     "));
                innertable5.addCell(cell5);
                cell5 = new PdfPCell(new Phrase("     "));
                innertable5.addCell(cell5);*/

                PdfPTable nested4 = new PdfPTable(1);
                nested4.addCell("R: "+item[2]);
                nested4.addCell("A: "+gsco[0]);
                PdfPCell nesthousing4 = new PdfPCell(nested4);
                innertable5.addCell(nesthousing4);
                PdfPTable nested5 = new PdfPTable(1);
                nested5.addCell("R: "+item[3]);
                nested5.addCell("A: "+gsco[1]);
                PdfPCell nesthousing5 = new PdfPCell(nested5);
                innertable5.addCell(nesthousing5);

                cell5 = new PdfPCell(new Phrase(item[6]));
                cell5.setMinimumHeight(10f);
                innertable5.addCell(cell5);

            }



            doc.add(innertable5);
            //Chunk linebreak4 = new Chunk(new LineSeparator());
            //doc.add(linebreak4);



            //next step
            PdfPTable innertable6 = new PdfPTable(3);
            innertable6.setWidthPercentage(100);
            innertable6.setWidths(new int[]{20,20,20});
            PdfPCell cell6 = new PdfPCell(new Phrase("Total Invoice Amount In Words:"));
            innertable6.addCell(cell6);
            cell6 = new PdfPCell(new Phrase("Place Of Supply :"));
            innertable6.addCell(cell6);
            cell6 = new PdfPCell(new Phrase("Total Amount:"+total));
            innertable6.addCell(cell6);
            doc.add(innertable6);
            // Chunk linebreak5 = new Chunk(new LineSeparator());
            // doc.add(linebreak5);

            PdfPTable innertable7 = new PdfPTable(3);
            innertable7.setWidthPercentage(100);
            innertable6.setWidths(new int[]{20,20,20});
            PdfPCell cell7 = new PdfPCell(new Phrase("Terms and Condition"));
            cell7.setVerticalAlignment(Element.ALIGN_TOP);
            innertable7.addCell(cell7);
            cell7 = new PdfPCell(new Phrase("Common Seal"));
            cell7.setVerticalAlignment(Element.ALIGN_BOTTOM);
            innertable7.addCell(cell7);
            cell7 = new PdfPCell(new Phrase("Authorised Signatory"));
            cell7.setMinimumHeight(100f);
            cell7.setVerticalAlignment(Element.ALIGN_BOTTOM);
            innertable7.addCell(cell7);






            doc.add(innertable7);
            // Chunk linebreak6 = new Chunk(new LineSeparator());
            //doc.add(linebreak6);
            doc.close();




        }
        catch (DocumentException e)
        {
            e.printStackTrace();

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }



    }



}
