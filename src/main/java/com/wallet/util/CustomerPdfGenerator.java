package com.wallet.util;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.wallet.model.Account;
import com.wallet.model.Customer;
import com.wallet.model.CustomerDocuments;
import com.wallet.model.Transaction;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CustomerPdfGenerator {
	
   private static final DeviceRgb HEADER_BG = new DeviceRgb(31,78,121);
   private static final DeviceRgb SECTION_BG = new DeviceRgb(220,234,247);
   private static final DeviceRgb ROW_ALT_BG = new DeviceRgb(245,249,253);
   private static final DeviceRgb BORDER_COLOR = new DeviceRgb(189,215,238);
   private static final DeviceRgb TEXT_MUTED = new DeviceRgb(100,116,139);
   private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd MM yyyy"); 

   private PdfFont bold,regular;
   
   public byte[] generateCustomerReport(Customer customer,List<CustomerDocuments> documents,List<Transaction> transactions) throws IOException{
	   ByteArrayOutputStream out = new ByteArrayOutputStream();
	   
	   try(Document doc = new Document(new PdfDocument(new PdfWriter(out)), PageSize.A4)){
		   doc.setMargins(36, 36, 36, 36);
		   
		   bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
		   regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
		   
		   addBanner(doc);
		   addPersonalInfo(doc,customer);
		   addAddress(doc,customer);
		   addPlanDetails(doc,customer);
		   addAccounts(doc,customer);
		   addTransactions(doc,transactions);
		   addDocuments(doc,documents);
		   
		   doc.add(new Paragraph("- End of Report -")
				   .setFont(regular)
				   .setFontSize(9)
				   .setFontColor(TEXT_MUTED)
				   .setTextAlignment(TextAlignment.CENTER)
				   );
		   
	   }
	   return out.toByteArray();
   }
   
   private void addBanner(Document doc) {
	   Table banner = fullWidthTable(1)
			   .setBackgroundColor(HEADER_BG)
			   .setBorderRadius(new BorderRadius(6))
			   .setMarginBottom(20);
	   
	   Cell cell = noBoderCell().setPadding(20);
	   
	   cell.add(new Paragraph("Customer report")
			   .setFont(bold)
			   .setFontSize(22)
			   .setFontColor(ColorConstants.WHITE)
			   );
	   
	   cell.add(new Paragraph("Generated on :"+ LocalDate.now().format(DATE_FMT))
			   .setFont(regular)
			   .setFontSize(10)
			   .setFontColor(new DeviceRgb(189,215,238))
			   );
	   
	   doc.add(banner.addCell(cell));
	   
	   
   }
   
   private void addPersonalInfo(Document doc ,Customer c ) throws IOException{
	   addSectionTitle(doc,"Personal Information");
	   
	   Table t = twoColTable();
	   
	   addRow(t, "Customer Id" , String.valueOf(c.getCustomerid()));
	   addRow(t, "First Name", c.getFirstname());
	   addRow(t,"Last Name", c.getLastname());
	   addRow(t,"Email", c.getEmailid());
	   addRow(t,"Contact", c.getContact());
	   addRow(t,"Gender",nvlEnum(c.getGendervalue()));
	   addRow(t,"Registration Form ", fmtDate(c.getRegisterationdate()));
	   
	   doc.add(t);
	   doc.add(spacer());
	   
   }
   
   private void addAddress(Document doc , Customer c) throws IOException{
	   if(c.getAddress()==null) return;
	   
	   addSectionTitle(doc,"Address");
	   
	   Table t = twoColTable();
	   addRow(t,"Address Line 1", nvl(c.getAddress().getAddressline1()));
	   addRow(t,"Address Line 2" , nvl(c.getAddress().getAddressline2()));
	   addRow(t,"City", nvl(c.getAddress().getCity()));
	   addRow(t,"State", nvl(c.getAddress().getState()));
	   addRow(t,"Pincode",nvl(c.getAddress().getPincode()));
	   
	   doc.add(t);
	   doc.add(spacer());
   }
   
   private void addPlanDetails(Document doc , Customer c) throws IOException{
	   addSectionTitle(doc,"Plan Details");
	   Table t = twoColTable();   
	   addRow(t,"Plan Type", nvlEnum(c.getPlanType()));
	   addRow(t,"Plan Start Date", fmtDate(c.getPlanStartDate()));
	   addRow(t,"Plan Expiry Date", fmtDate(c.getPlanExpiryDate()));
	   
	   doc.add(t);
	   doc.add(spacer());
	   doc.add(spacer());
   }
   
   private void addAccounts(Document doc , Customer c) throws IOException{
	   List<Account> accounts = c.getAccounts();
	   addSectionTitle(doc, "Accounts (" + (accounts != null?accounts.size() :0)+")");
	   
	   if(accounts==null || accounts.isEmpty()) {
		   doc.add(emptyMsg("No Accounts Found. "));
	   }else {
		   Table t = fullWidthTable(1,2,2,2,3);
		   
		   addTableHeaders(t,"Acc No.","Type","Opening Balance", "Opening Date","Description");
		   
		   boolean alt = false;
		   for(Account a : accounts) {
			   addTableRow(t,alt,
					   String.valueOf(a.getAccountnumber()),
					   nvlEnum(a.getTypeofaccount()),
					   String.format("%.2f", a.getOpeningbalance()),
					   fmtDate(a.getOpeningdate()),
					   nvl(a.getDescription())
					   );
			   alt =!alt;
		   }
		   doc.add(t);
	   }
	   doc.add(spacer());
   }
   
    private void addTransactions(Document doc, List<Transaction> transactions) throws IOException{
    	addSectionTitle(doc,"Transactions (" + size(transactions)+")");
    	
    	if(transactions == null || transactions.isEmpty()) {
    		doc.add(emptyMsg("No Transactions Found."));
    	}
    	else {
    		Table t = fullWidthTable(1,2,2,2,2,3);
    		addTableHeaders(t,"Tx ID", "Type", "Amount", "Date","From Acc","To Acc");
    		
    		boolean alt = false;
    		for(Transaction tx :transactions) {
    			addTableRow(t,alt,
    					String.valueOf(tx.getTransactionid()),
    					nvlEnum(tx.getTransactionType()),
    					String.format("%.2f", tx.getAmount()),
    					fmtDate(tx.getTransactiondate()),
    					tx.getFromAccount() != null? String.valueOf(tx.getFromAccount().getAccountnumber()):"-",
    					tx.getToAccount() !=null? String.valueOf(tx.getToAccount().getAccountnumber()):"-"	
    							
    					);
    			
    			alt=!alt;
    		}
    		doc.add(t);
    		
    	} doc.add(spacer());
    }
   
    private void addDocuments(Document doc , List<CustomerDocuments> documents) throws IOException{
    	addSectionTitle(doc,"Uploaded Documents (" +size(documents)+ ")");
    	
    	if(documents== null ||documents.isEmpty()) {
    		doc.add(emptyMsg("No Docs Uploaded"));
    	}
    	else {
    		Table t = fullWidthTable(1,2,2,2,1);
    		addTableHeaders(t,"Doc Id","Document Type","File Name","Upload Date","Status");
    		boolean alt = false;
    		for(CustomerDocuments d : documents) {
    			addTableRow(t,alt,
    					String.valueOf(d.getCustdocid()),
    					nvlEnum(d.getDocname()),
    					nvl(d.getFileName()),
    					fmtDate(d.getDocuploaddate()),
    					d.isStatus()? "Active" : "Inactive"
    					);
    			alt = !alt;
    			
    		}
    		doc.add(t);
    		
    		
    	}doc.add(spacer());
    }

    private void addSectionTitle(Document doc ,String title) {
    	doc.add(fullWidthTable(1)
    			.setBackgroundColor(SECTION_BG)
    			.setMarginBottom(4)
    			.addCell(new Cell()
    					.setBorder(new SolidBorder(BORDER_COLOR,1))
    					.setPaddingLeft(10)
    					.setPaddingTop(6)
    					.setPaddingBottom(6)
    					.add(new Paragraph(title)
    							.setFont(bold)
    							.setFontSize(11)
    							.setFontColor(HEADER_BG)
    					)));
    }
    
    private void addRow(Table table, String label, String value) {
    	table.addCell(new Cell()
    			.setBackgroundColor(ROW_ALT_BG)
    			.setBorder(new SolidBorder(BORDER_COLOR,0.5f))
    			.setPadding(7)
    			.add(new Paragraph(label)
    					.setFont(bold)
    					.setFontSize(9)
    					.setFontColor(TEXT_MUTED)
    					));
    	
    	table.addCell(new Cell()
    			.setBackgroundColor(ROW_ALT_BG)
    			.setBorder(new SolidBorder(BORDER_COLOR,0.5f) )
    			.setPadding(7)
    			.add(new Paragraph(value)
    					.setFont(regular).setFontSize(9)
    					));
    }
    
    private void addTableHeaders(Table table , String...headers) {
    	for(String h : headers) {
    		table.addHeaderCell(
    				new Cell()
    				.setBackgroundColor(HEADER_BG)
    				.setBorder(Border.NO_BORDER)
                    .setPadding(7)
                    .add(new Paragraph(h).setFont(bold).setFontSize(9).setFontColor(ColorConstants.WHITE))
    				); 
    	}
    }
    private void addTableRow(Table table, boolean alt, String... values) {
        DeviceRgb bg = alt ? ROW_ALT_BG : (DeviceRgb) ColorConstants.WHITE;
        for (String val : values) {
            table.addCell(new Cell()
                    .setBackgroundColor(bg)
                    .setBorder(new SolidBorder(BORDER_COLOR, 0.5f))
                    .setPadding(6)
                    .add(new Paragraph(nvl(val)).setFont(regular).setFontSize(9)));
        }
    }

   

    private Table fullWidthTable(float... cols) {
        return new Table(UnitValue.createPercentArray(cols)).useAllAvailableWidth();
    }

    private Table twoColTable() {
        return fullWidthTable(2, 3).setMarginBottom(4);
    }

    private Cell noBoderCell() {
        return new Cell().setBorder(Border.NO_BORDER);
    }

    private Paragraph spacer(){ return new Paragraph("\n"); }
    private int size(List<?> l){ return l != null ? l.size() : 0; }
    private String nvl(String v){ return v != null && !v.isBlank() ? v : "—"; }
    private String nvlEnum(Enum<?> e){ return e != null ? e.name() : "—"; }
    private String fmtDate(java.time.LocalDate d) { return d != null ? d.format(DATE_FMT) : "—"; }
    private Paragraph emptyMsg(String msg) {
        return new Paragraph(msg)
        		.setFont(regular)
        		.setFontSize(9)
                .setFontColor(TEXT_MUTED)
                .setItalic()
                .setMarginLeft(10);
    }
    
}
