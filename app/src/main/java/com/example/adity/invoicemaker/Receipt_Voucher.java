package com.example.adity.invoicemaker;


import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Receipt_Voucher {


    public void pdfcreate() {
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document(PageSize.A4.rotate(), 0f, 0f, 0f, 0f);


        String outPath = Environment.getExternalStorageDirectory() + "/mypdff.pdf";

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(outPath));
            doc.open();
            doc.setMargins(0, 0, 0, 0);           // setting margin
            PdfPTable innertable = new PdfPTable(1);  //first table
            innertable.setWidthPercentage(100);

            //  innertable.setWidths(new int[]{40});

            PdfPCell cell = new PdfPCell(new Paragraph("COMPANY NAME", FontFactory.getFont(FontFactory.TIMES_BOLD, 17, Font.NORMAL, BaseColor.BLACK)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable.addCell(cell);

// column 3


            cell = new PdfPCell(new Paragraph("ADDRESS LINE 1", FontFactory.getFont(FontFactory.TIMES_BOLD, 17, Font.NORMAL, BaseColor.BLACK)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable.addCell(cell);

// column 4


            cell = new PdfPCell(new Paragraph("PHONE NO", FontFactory.getFont(FontFactory.TIMES_BOLD, 17, Font.NORMAL, BaseColor.BLACK)));
            //cell.setPaddingLeft(2);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable.addCell(cell);


            cell = new PdfPCell(new Paragraph("GSTIN", FontFactory.getFont(FontFactory.TIMES_BOLD, 17, Font.NORMAL, BaseColor.BLACK)));
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

//Second table

            PdfPTable inner = new PdfPTable(1);
            inner.setWidthPercentage(100);
            PdfPCell cel = new PdfPCell(new Phrase("RECEIPT VOUCHER",
                    FontFactory.getFont(FontFactory.COURIER_BOLD, 25, Font.NORMAL, BaseColor.BLACK)));

            cel.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
            inner.addCell(cel);
            cel = new PdfPCell();
            cel.setColspan(5);
            cel.setFixedHeight(6);
            cel.setBorder(Rectangle.NO_BORDER);
            inner.addCell(cel);

            doc.add(inner);

            //  innertable.addCell(cell);
            PdfPTable innertable2 = new PdfPTable(2);
            innertable2.setWidthPercentage(100);
            innertable2.setWidths(new int[]{50, 50});
            PdfPCell cell1 = new PdfPCell(new Phrase("Voucher number:"));
            //cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Details of Receiver"));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell1.setPaddingLeft(20);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Voucher Date:"));

            innertable2.addCell(cell1);


            cell1 = new PdfPCell(new Phrase("Name:"));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingLeft(2);

            innertable2.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("Place of supply:"));
            //cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Address :"));
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Reverse Charge (Y/N):"));
            innertable2.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(""));
            cell.setPaddingLeft(2);
            innertable2.addCell(cell1);
            cell1 = new PdfPCell(new Phrase(""));
            innertable2.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("GSTIN :"));
            cell.setPaddingLeft(2);
            innertable2.addCell(cell1);
            cell1 = new PdfPCell(new Phrase("State:  \t\t\t  Code:"));

            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            innertable2.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("State:         Code: "));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);

            innertable2.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setColspan(5);
            cell1.setFixedHeight(6);
            cell1.setBorder(Rectangle.NO_BORDER);
            innertable2.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setColspan(5);
            cell1.setFixedHeight(6);
            cell1.setBorder(Rectangle.NO_BORDER);
            innertable2.addCell(cell1);

            doc.add(innertable2);


            PdfPTable innertable5 = new PdfPTable(6);
            innertable5.setWidthPercentage(100);
            // innertable5.setWidths(new int[]{11,4,7,5,5,5});

            PdfPCell cell5 = new PdfPCell(new Phrase("Description of Product/Service"));
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("HSN code"));
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("Taxable Value"));
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            innertable5.addCell(cell5);

            cell5 = new PdfPCell(new Phrase("SGST"));
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            innertable5.addCell(cell5);

            cell5 = new PdfPCell(new Phrase("CGST"));
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            innertable5.addCell(cell5);

            cell5 = new PdfPCell(new Phrase("Total Advance Received"));
            cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell5.setMinimumHeight(10f);
            innertable5.addCell(cell5);




         /*   for(int i=0;i<items.size();i++)
            {
                String item[]=items.get(i);
                String gsco[]=GST.get(i);*/

            cell5 = new PdfPCell(new Phrase(""));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase(""));
            innertable5.addCell(cell5);
            cell5 = new PdfPCell(new Phrase("     "));
            innertable5.addCell(cell5);
            PdfPTable nested4 = new PdfPTable(1);
            nested4.addCell("R: ");
            nested4.addCell("A: ");
            PdfPCell nesthousing4 = new PdfPCell(nested4);
            innertable5.addCell(nesthousing4);
            PdfPTable nested5 = new PdfPTable(1);
            nested5.addCell("R: ");
            nested5.addCell("A: ");
            PdfPCell nesthousing5 = new PdfPCell(nested5);
            innertable5.addCell(nesthousing5);
            cell5 = new PdfPCell(new Phrase("0"));
            cell5.setMinimumHeight(10f);
            innertable5.addCell(cell5);
            doc.add(innertable5);
//}
            PdfPTable t = new PdfPTable(5);
            t.setWidthPercentage(100);
            t.setWidths(new int[]{14, 7, 7, 7, 7});
            PdfPCell ce = new PdfPCell(new Phrase("Total"));
            t.addCell(ce);
            ce = new PdfPCell(new Phrase("0"));
            t.addCell(ce);
            ce = new PdfPCell(new Phrase("0"));
            t.addCell(ce);
            ce = new PdfPCell(new Phrase("0"));
            t.addCell(ce);
            ce = new PdfPCell(new Phrase("0"));
            t.addCell(ce);
            doc.add(t);


            //next step
            PdfPTable innertable6 = new PdfPTable(1);
            innertable6.setWidthPercentage(100);
            PdfPCell cell6 = new PdfPCell(new Phrase("Total Amount Paid (In Words:)"));
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            innertable6.addCell(cell6);
            cell6.setMinimumHeight(50f);
            cell6 = new PdfPCell(new Phrase("dfskmdkmkmlldskmf"));
            cell6.setPaddingLeft(20);
            innertable6.addCell(cell6);
            cell6.setMinimumHeight(10f);
            cell6 = new PdfPCell();
            cell6.setColspan(5);
            cell6.setFixedHeight(6);
            cell6.setBorder(Rectangle.NO_BORDER);
            innertable6.addCell(cell6);
            doc.add(innertable6);

            PdfPTable innertable7 = new PdfPTable(4);
            innertable7.setWidthPercentage(100);
            //innertable6.setWidths(new int[]{20,20,20});
            PdfPCell cell7 = new PdfPCell(new Phrase("Authorised Signatory"));
            cell7.setMinimumHeight(100f);
            cell7.setVerticalAlignment(Element.ALIGN_BOTTOM);
            innertable7.addCell(cell7);
            cell7 = new PdfPCell(new Phrase("Common Seal"));
            cell7.setMinimumHeight(100f);
            cell7.setVerticalAlignment(Element.ALIGN_BOTTOM);
            innertable7.addCell(cell7);

            PdfPTable nested = new PdfPTable(1);
            nested.addCell("Total Amount before tax:");
            nested.addCell("Add: CGST");
            nested.addCell("Add: SGST");
            nested.addCell("Total Tax Amount (GST)");
            nested.addCell("Total Amount After Tax");
            nested.addCell("GST on Reverse Charge");
            PdfPCell nesthousing = new PdfPCell(nested);

            innertable7.addCell(nesthousing);
            PdfPTable nested2 = new PdfPTable(1);
            nested2.addCell("0");
            nested2.addCell("0");
            nested2.addCell("0");
            nested2.addCell("0");
            nested2.addCell("0");
            nested2.addCell("0");
            PdfPCell nesthousing2 = new PdfPCell(nested2);
            innertable7.addCell(nesthousing2);

            doc.add(innertable7);
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}